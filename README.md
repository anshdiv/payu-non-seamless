PayU non-seamless
============================
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![](https://jitpack.io/v/anshdiv/payu-non-seamless.svg)](https://jitpack.io/#anshdiv/payu-non-seamless)


Prerequisites
-----
1. Create Payu Merchant account and complete profile by submitting the documents.<br />
2. Contact PayU customer executive to activate account.<br />
3. Find PayU Merchant key and Salt from Payu Account section.<br />

Note:- Without account activation or invalid merchant key,salt will cause an error like below. <br/>
 <img src="https://i.stack.imgur.com/YUjAX.png" width="350"/>

There are two modes for this:
1. `TEST_MODE` - For testing purpose ,PayU Test Card Credentials will work on this mode.
2. `PRODUCTION_MODE` - for production purpose and start accepting payments.



Usage
-----

Create an PayConfig object and set the values accordingly:
```java
 PayUConfig payUConfig = new PayUConfig();
        payUConfig.setAmount("200");
        payUConfig.setFirstname("Anshul Gour");
        payUConfig.setProductInfo("Shopping");
        payUConfig.setEmail("abc@gmail.com");
        payUConfig.setPhonenumber("999999999");
```

StartActivity for result
```java
startActivityForResult(new Intent(this, PayUMoneyActivity.class), PayU.REQUEST_CODE);
```
Handle the response 
```java
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
```
Don't forget to mention PayMoneyActivity in Manifest file

```Manifest
<activity android:name=".payu.PayUMoneyActivity"/>
```

Download
-------

1. Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
2. Add the dependency
```groovy
dependencies {
	        compile 'com.github.anshdiv:payu-non-seamless:1.0.0'
	}
```

License
-------

      Copyright 2018 Anshul GOur
   
      Licensed under the Apache License, Version 2.0 (the "License");
      you may not use this file except in compliance with the License.
      You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

      Unless required by applicable law or agreed to in writing, software
      distributed under the License is distributed on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      See the License for the specific language governing permissions and
      limitations under the License.
