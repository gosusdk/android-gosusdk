GosuSDK for Android
========================

* Authentication
* Billing
* Tracking

INSTALLATION
------------

**Download the official version: [click here](https://github.com/gosusdk/android-gosusdk/releases)**

#### 1. In your root-level (project-level) Gradle file `<project>/build.gradle`, add more plugins dependency to your `build.gradle` file:

```gradle
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url "https://sdk-download.airbridge.io/maven" }
    }
}
dependencies {
    // ...
    // google service (use firebase tracking)
    classpath 'com.google.gms:google-services:4.3.15'
}
```	
#### 2. In your module (app-level) Gradle file `<project>/<app-module>/build.gradle`, add more plugins dependency to your `build.gradle` file:

```gradle
// google service plugin (use firebase tracking)
apply plugin: 'com.google.gms.google-services'

dependencies {
    // ...
    // GosuSDK
    implementation files('libs/gosusdk.aar')
    //for in app billing
    implementation 'com.android.billingclient:billing:6.0.1'
    //for appsflyer
    implementation 'com.appsflyer:af-android-sdk:6.3.2'
    implementation 'com.android.installreferrer:installreferrer:2.2'
    //for showLogin facebook sdk
    implementation 'com.facebook.android:facebook-android-sdk:latest.release'
    //for sigin GG SDK
    implementation 'com.google.android.gms:play-services-auth:20.6.0'
    //for firebase
    // Import the BoM for the Firebase platform
    implementation platform('com.google.firebase:firebase-bom:31.1.0')
    implementation 'com.google.guava:guava:31.1-android'
    implementation 'com.google.firebase:firebase-messaging:23.2.1'
    implementation 'com.google.firebase:firebase-analytics'
    // GRPC Deps
    implementation 'io.grpc:grpc-okhttp:1.57.1'
    implementation 'io.grpc:grpc-protobuf-lite:1.57.1'
    implementation 'io.grpc:grpc-stub:1.57.1'
    compileOnly 'org.apache.tomcat:annotations-api:6.0.53'
    //airbridge
    implementation "io.airbridge:sdk-android:2.22.0"
}
```	
####-Move config file (google-services.json) into the module (app-level) root directory of your app.
```
app/
  google-services.json
```

####- Add gosu-service.json file to folder main/assets
```json
{
  "client_id": "",
  "airb_app_name": "sdkgosutest",
  "airb_app_token": "d878da2af447440385fe9a4fe37b06a0"
}
```
#### 3. Edit Your Resources and Manifest
**- Open the /app/res/values/strings.xml file.**
```xml
<string name="facebook_app_id">1234</string>
<string name="fb_login_protocol_scheme">fb1234</string>
<string name="facebook_client_token">56789</string>
```
**-Open the /app/manifest/AndroidManifest.xml file.**
```xml
<!-- ============ PERMISSION ============== -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<!-- use for Push GSM -->
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
<!-- use for iab -->
<uses-permission android:name="com.android.vending.BILLING" />
<uses-permission android:name="com.google.android.gms.permission.AD_ID" />

<!-- ============ Facebook META config ============== -->
<meta-data
    android:name="com.facebook.sdk.ApplicationId"
    android:value="@string/facebook_app_id"/>
<meta-data
    android:name="com.facebook.sdk.ClientToken"
    android:value="@string/facebook_client_token" />
<!-- ======= AF Tracking ======= -->
<receiver
    android:name="com.appsflyer.MultipleInstallBroadcastReceiver"
    android:exported="true" >
    <intent-filter>
        <action android:name="com.android.vending.INSTALL_REFERRER" />
    </intent-filter>
</receiver>

<receiver
    android:name="com.appsflyer.SingleInstallBroadcastReceiver"
    android:exported="true" >
    <intent-filter>
        <action android:name="com.android.vending.INSTALL_REFERRER" />
    </intent-filter>
</receiver>
<!-- ============ Google/Facebook Activity ============== -->
<activity
    android:name="com.game.gsoauth.GoogleManager$SignInActivity"
    android:screenOrientation="fullSensor"
    tools:ignore="Instantiatable">
</activity>
<activity
    android:name="com.game.gsoauth.FacebookManager$SignInActivity"
    android:screenOrientation="fullSensor"
    tools:ignore="Instantiatable">
</activity>
```
USAGE GOSU LOGIN SDK
--------------------
### 1. Initialize configuration for GosuSDK
---
#### You can use one of the two following approaches to initialize the delegate for the oauthCallback:
##### Approach 1: Using Anonymous Inner Class
```java
    public class MainActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            //...
            /* Initialize authCallback delegate */
            GosuSDK.getInstance().setOauthListener(new IGameOauthListener() {
                @Override
                public void onLoginSuccess(String UserId, String UserName, String accesstoken) { }
                @Override
                public void onLogout() {}
                @Override
                public void onError() {}
                @Override
                public void onDeleteAccount(String status) {}
            });
            /* Initialize GosuSDK */
            GosuSDK.getInstance().initSdk(this);
        }
    }
```
##### Approach 2: Using Interface Implementation
```java
    public class MainActivity extends AppCompatActivity implements IGameOauthListener {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            //...
            /* Initialize authCallback delegate */
            GosuSDK.getInstance().setOauthListener(this);
            /* Initialize GosuSDK */
            GosuSDK.getInstance().initSdk(this);
        }
        /* Delegate */
        @Override
        public void onLoginSuccess(String UserId, String UserName, String accesstoken) {
        }    
        @Override
        public void onLogout() {
        }    
        @Override
        public void onError() {
        }    
        @Override
        public void onDeleteAccount(String status) {
        }
    }
```
**NOTE**
* Sign in with Google: You send SHA-1 us [click here](https://developers.google.com/android/guides/client-auth)
* Sign in with Facebook: You send hash key us [more here](https://developers.facebook.com/docs/facebook-login/android)

### 2. GosuSDK Basic Functions
---
```java
//init SDK and show SignIn
GosuSDK.getInstance().initSdk(this);
//init SDK
GosuSDK.getInstance().onlyInitSdk(this);
//The result will return an IGameOauthListener delegate.
GosuSDK.getInstance().showSignIn();
GosuSDK.getInstance().logout();
GosuSDK.getInstance().deleteAccount();
```
### 3. Make payments through Google Billing IAP for in-app purchases.
---
```java
public void call_billing()
{
    String productID = "com.flashpoint.nemo.100kc"; //GameConstant.iap_product_ids.get(0);
    String mProductName = "Mua gói 100KNB";
    String amount = "22000";
    String orderID = Utility.getInstance().randomString(10); //random string your
    String serverID       = "S1";
    String characterID    = "Character_ID";
    String extraInfo    = "";

    GameItemIAPObject gosuItemIAPObject = new GameItemIAPObject(
        productID,
        mProductName,
        orderID,
        amount,
        serverID,
        characterID,
        extraInfo
    );
    GosuSDK.getInstance().showIAP(gosuItemIAPObject, new IGamePaymentListener() {
        @Override
        public void onPaymentSuccess(String message) {
        }
        @Override
        public void onPaymentError(String message) {
        }
    });
    /**
     * productID:ID of the product
     * productName:Name of the product
     * amount:	Price of the item
     * serverID: ID of the server
     * characterID: ID of the character
     * extraInfo: Additional information that partners can send, which will be sent to the API to add gold after IAP payment.
     */
}
```

USAGE GOSU TRACKING SDK
--------------------

```java
GTrackingManger.getInstance().trackingStartTrial();
GTrackingManger.getInstance().trackingTutorialCompleted();
GTrackingManger.getInstance().doneNRU(
        "server_id",
        "role_id",
        "Role Name"
);
/* custom event */
GTrackingManger.getInstance().trackingEvent("level_20");
GTrackingManger.getInstance().trackingEvent("level_20", "{\"customer_id\":\"1234\"}");
/* example: 
jsonContent = {"event": "event_name", "params": {"key": "value", "key2": "value2"} }
*/
JSONObject jsonContent = new JSONObject();
jsonRole.put("character", "CharacterName");
jsonRole.put("server", "ServerID");        
GTrackingManger.getInstance().trackingEvent("event_name", jsonContent);
```
