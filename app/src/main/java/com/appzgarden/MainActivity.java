package com.appzgarden;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appzgarden.payu.PayU;
import com.appzgarden.payu.PayUConfig;
import com.appzgarden.payu.PayUMoneyActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PayUConfig payUConfig = new PayUConfig();
        payUConfig.setAmount("200");
        payUConfig.setFirstname("Anshul Gour");
        payUConfig.setProductInfo("Shopping");
        payUConfig.setEmail("abc@gmail.com");
        payUConfig.setPhonenumber("999999999");

        startActivityForResult(new Intent(this, PayUMoneyActivity.class), PayU.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case PayU.SUCESS_RESULT_CODE:
                // do something
                break;
            case PayU.FAIL_RESULT_CODE:
                // do something
                break;
             case PayU.CANCEL_RESULT_CODE:
                 // do something
                 break;
        }

    }
}
