package com.appzgarden.payu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by anshul on 22-01-2018.
 */


@SuppressLint("SetJavaScriptEnabled")
public class PayUMoneyActivity extends Activity {

    private WebView webView;

    private String action1 = "";
    private String base_url = "";
    private String txnid = "";

    private PayUConfig payUConfig;
    private String SUCCESS_URL = "";

    private Handler mHandler = new Handler();
    private PayUMoneyActivity activity;
    private HashMap<String, String> params;
    private String salt = "";
    private ProgressDialog progressDialog;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        webView = new WebView(this);
        setContentView(webView);
        activity = this;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        params = new HashMap<>();
        try {

            init((PayUConfig) getIntent().getSerializableExtra(PayU.PAYUCONFIG));
        } catch (PayUConfigNotfoundException e) {
            e.printStackTrace();
        }
        Random rand = new Random();
        String rndm = Integer.toString(rand.nextInt())
                + (System.currentTimeMillis() / 1000L);
        txnid = hashCal("SHA-256", rndm).substring(0, 20);
        params.put("txnid", txnid);
        String hash = hashCal("SHA-512", params.get("key") + "|" + params.get("txnid") + "|" + params.get("amount")
                + "|" + params.get("productinfo") + "|" + params.get("firstname") + "|" + params.get("email")
                + "|||||||||||" + salt);

        action1 = base_url.concat("/_payment");

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                Toast.makeText(activity, "Oh no! " + description,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                Toast.makeText(activity, "SslError! " + error,
                        Toast.LENGTH_SHORT).show();
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Toast.makeText(activity, "Page Started! " + url,
                        Toast.LENGTH_SHORT).show();
                if (url.equals(SUCCESS_URL)) {
                    Toast.makeText(activity, "Success! " + url,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Failure! " + url,
                            Toast.LENGTH_SHORT).show();
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
            //
            // @Override
            // public void onPageFinished(WebView view, String url) {
            // super.onPageFinished(view, url);
            //
            // Toast.makeText(PayMentGateWay.this, "" + url,
            // Toast.LENGTH_SHORT).show();
            // }
        });

        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setCacheMode(2);
        webView.getSettings().setDomStorageEnabled(true);
        webView.clearHistory();
        webView.clearCache(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setUseWideViewPort(false);
        webView.getSettings().setLoadWithOverviewMode(false);

        webView.addJavascriptInterface(new PayUJavaScriptInterface(activity),
                "PayUMoney");
        params.put("hash", hash);
        webview_ClientPost(webView, action1, params.entrySet());

    }

    public class PayUJavaScriptInterface {
        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        PayUJavaScriptInterface(Context c) {
            mContext = c;
        }

        /**
         * This is not called on the UI thread. Post a runnable to invoke
         * loadUrl on the UI thread.
         */
        @JavascriptInterface
        public void success(long id, final String paymentId) {
            mHandler.post(new Runnable() {
                public void run() {
                    mHandler = null;
                    Intent intent = new Intent();
                    intent.putExtra(PayU.PAYMENT_ID, paymentId);
                    setResult(PayU.SUCESS_RESULT_CODE);
                    finish();
                }
            });
        }

        @JavascriptInterface
        public void failure(final String id, final String error) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.putExtra(PayU.PAYMENT_ERROR, error);
                    setResult(PayU.FAIL_RESULT_CODE);
                    finish();
                }
            });
        }

        @JavascriptInterface
        public void failure(final String params) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    setResult(PayU.CANCEL_RESULT_CODE);
                    finish();
                }
            });
        }

    }

    public void webview_ClientPost(WebView webView, String url,
                                   Collection<Map.Entry<String, String>> postData) {
        StringBuilder sb = new StringBuilder();

        sb.append("<html><head></head>");
        sb.append("<body onload='form1.submit()'>");
        sb.append(String.format("<form id='form1' action='%s' method='%s'>",
                url, "post"));
        for (Map.Entry<String, String> item : postData) {
            sb.append(String.format(
                    "<input name='%s' type='hidden' value='%s' />",
                    item.getKey(), item.getValue()));
        }
        sb.append("</form></body></html>");
        Log.d("Payu Tag", "webview_ClientPost called");
        webView.loadData(sb.toString(), "text/html", "utf-8");
    }

    public String hashCal(String type, String str) {
        byte[] hashseq = str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();

            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1)
                    hexString.append("0");
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException nsae) {
        }
        return hexString.toString();

    }

    private void setmode(int mode) {
        switch (mode) {
            case PayU.TEST_MODE:
                base_url = PayU.TESTURL;
                break;
            case PayU.PRODUCTION_MODE:
                base_url = PayU.PRODUCTIONURL;
                break;
            default:
                base_url = PayU.TESTURL;
                break;
        }
    }

    private void init(PayUConfig payUConfig) throws PayUConfigNotfoundException {
        if (payUConfig == null) {
            throw new PayUConfigNotfoundException("PayUConfig not found");
        } else {

            setmode(payUConfig.getMode());
            params.put("key", payUConfig.getMerchantKey());

            params.put("amount", payUConfig.getAmount());
            params.put("firstname", payUConfig.getFirstname());
            params.put("email", payUConfig.getEmail());
            params.put("phone", payUConfig.getPhonenumber());
            params.put("productinfo", "Recharge Wallet");
            params.put("surl", payUConfig.getSuccessURL());
            params.put("furl", payUConfig.getFailURL());
            params.put("service_provider", "payu_paisa");
            params.put("lastname", payUConfig.getLastname());
            params.put("address1", payUConfig.getAddress1());
            params.put("address2", payUConfig.getAddress2());
            params.put("city", payUConfig.getCity());
            params.put("state", payUConfig.getState());
            params.put("country", payUConfig.getCountry());
            params.put("zipcode", payUConfig.getZipcode());
            params.put("udf1", "");
            params.put("udf2", "");
            params.put("udf3", "");
            params.put("udf4", "");
            params.put("udf5", "");
            params.put("pg", "");
            salt = payUConfig.getSalt();
            SUCCESS_URL = payUConfig.getSuccessURL();
        }
    }
}

