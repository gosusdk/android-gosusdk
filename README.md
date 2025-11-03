GosuSDK for Android v1.1.0
========================

**Latest Gaming SDK with Enhanced Features**

* Authentication & User Verification
* Billing & Payment
* Event Tracking & Analytics
* Android 15+ 16KB Page Size Compatibility

## What's New in v1.1.0

### 🆕 **Added New**
- **ITS SDK Integration**: Brand new ITS SDK v1.1.1 integrated for enhanced analytics
  - Advanced event tracking and user behavior analytics
  - Real-time data collection and processing

### 🗑️ **Removed**
- **Airbridge Integration**: Completely removed Airbridge SDK dependency for simplified integration
- **AppsFlyer Integration**: Completely removed AppsFlyer SDK dependency for simplified integration

### 🔄 **Updated**
- **SQLCipher Library**: Updated to v4.10.0 with 16KB page size compatibility
- **Build Environment**: Updated to Gradle 8.7 and Android Gradle Plugin 8.5.1
- **Google Policy Compliance**: Full support for new Google Play Store 16KB page size requirements
- **ITS SDK**: Updated to latest v1.1.1 with enhanced analytics capabilities

### 🔧 **Enhanced**
- **16KB Page Size Compatibility**: Added support for Android 15+ requirements
  - Future-proof native library building for upcoming Android devices
  - Enhanced SQLCipher configuration with proper 16KB page alignment
  - Optimized memory alignment and performance

### 📋 **Configuration Updates**
- **Gradle Properties**: Enhanced for 16KB page size compatibility
- **Native Library Alignment**: Optimized for better performance and compatibility
  
## Core Features
### 🔧 **System Requirements**
- **Modern Build System**: Gradle 8.7, AGP 8.5.1, Java 17
- **Latest Android**: Target SDK 35, Compile SDK 35
- **Updated Dependencies**: Android Billing Client 7.0.0, Firebase latest versions
- **ITS SDK 1.1.1**: Enhanced analytics and tracking capabilities

INSTALLATION
------------

**Download the official version: [click here](https://github.com/gosusdk/android-gosusdk/releases)**

#### 1. In your root-level (project-level) Gradle file `<project>/build.gradle`, add more plugins dependency to your `build.gradle` file:

```gradle
allprojects {
    repositories {
        google()
        mavenCentral()
        // Airbridge repository removed in v1.1.0
        // maven { url "https://sdk-download.airbridge.io/maven" }
    }
}
dependencies {
    // ...
    // google service (use firebase tracking & firebase analytic)
    classpath 'com.android.tools.build:gradle:8.5.1'  // Updated for v1.1.0
    classpath "com.google.protobuf:protobuf-gradle-plugin:0.9.4"
    classpath 'com.google.gms:google-services:4.4.2'
    classpath 'com.github.dcendents:android-maven-gradle-plugin:2.0'
    classpath 'com.google.firebase:firebase-crashlytics-gradle:3.0.2'
}
```	
#### 2. In your module (app-level) Gradle file `<project>/<app-module>/build.gradle`, add more plugins dependency to your `build.gradle` file:

```gradle
// google service plugin (use firebase tracking)
apply plugin: 'com.google.gms.google-services'
apply plugin: 'com.google.firebase.crashlytics'

android {
    compileSdk 35      // Updated for v1.1.0
    
    defaultConfig {
        targetSdk 35   // Updated for v1.1.0
        minSdkVersion 26
        versionName "1.1.0"
        multiDexEnabled true
        ndk.abiFilters 'armeabi-v7a','arm64-v8a','x86','x86_64'  // 16KB page size compatibility
        // ...
    }
    
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17 
        targetCompatibility JavaVersion.VERSION_17
    }
    namespace 'your.package.name'
}

dependencies {
    // GameSDK & ITS SDK v1.1.0
    implementation files('libs/gosusdk-v1.1.0.aar')
    implementation files('libs/its_sdk-v1.1.1.aar')
    implementation fileTree(dir: "libs", include: ["*.jar"])
    
    // Android Support Libraries
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    // GRPC Deps
    implementation 'io.grpc:grpc-okhttp:1.57.2'
    implementation 'io.grpc:grpc-protobuf-lite:1.57.2'
    implementation 'io.grpc:grpc-stub:1.57.2'
    compileOnly 'org.apache.tomcat:annotations-api:6.0.53'
    // biometric
    implementation "androidx.biometric:biometric:1.1.0"
    //remove airbridge
    //implementation "io.airbridge:sdk-android:2.22.2"
    //remove appsflyer
    //implementation 'com.appsflyer:af-android-sdk:6.3.2'
    implementation 'com.android.installreferrer:installreferrer:2.2'
    //for showLogin facebook sdk
    implementation 'com.facebook.android:facebook-android-sdk:latest.release'
    //for in app billing
    implementation 'com.android.billingclient:billing:7.0.0'
    implementation 'com.google.guava:guava:31.1-android'
    //for gson
    implementation 'com.google.code.gson:gson:2.10.1'
    //for sigin GG SDK
    implementation 'com.google.android.gms:play-services-auth:21.2.0'
    //for firebase
    implementation platform('com.google.firebase:firebase-bom:31.1.0')
    implementation 'com.google.firebase:firebase-analytics:21.2.0'
    implementation 'com.google.firebase:firebase-messaging:23.1.0'
    implementation("com.google.firebase:firebase-crashlytics")
    implementation 'com.google.android.material:material:1.9.0'
    implementation("com.google.android.play:review:2.0.1")
    implementation 'androidx.core:core:1.10.1'
    //update sqlcipher updated for v1.1.0
    //implementation "net.zetetic:sqlcipher-android:4.5.6@aar"
    implementation "net.zetetic:sqlcipher-android:4.10.0@aar"
    //
    implementation "androidx.sqlite:sqlite:2.3.1"
    implementation 'androidx.lifecycle:lifecycle-process:2.6.1'
    implementation 'androidx.lifecycle:lifecycle-common:2.6.1'
    implementation 'androidx.browser:browser:1.8.0'
    implementation 'com.rudderstack.android.sdk:core:1.25.1'

}
```	
**-Move config file (google-services.json) into the module (app-level) root directory of your app.**
```
app/
  google-services.json
```

**- Add gosu-service.json file to folder main/assets**
This file information will be sent separately by email by the product operator.
```json
{
  "client_id": "sample_value",
  "its_app_write_key": "sample_value",
  "its_app_signing_key": "sample_value"
}
```

**Note**: Airbridge configuration fields (`airb_app_name`, `airb_app_token`) have been removed in v1.1.0.

#### 4. Edit Your Resources and Manifest
**- Open the /app/res/values/strings.xml file.**

```xml
<string name="facebook_app_id">1234</string>
<string name="fb_login_protocol_scheme">fb1234</string>
<string name="facebook_client_token">56789</string>
```
##### 4.1 Add file config rule backup

**-Add new  /app/src/main/res/xml/backup_rules_11.xml**
```xml
<full-backup-content>
<exclude domain="sharedpref" path="its_prefs.xml"/>
<exclude domain="sharedpref" path="rl_prefs.xml"/>
</full-backup-content>
```

**-Add new  /app/src/main/res/xml/backup_rules_12.xml**
```xml
<data-extraction-rules>
<cloud-backup>
<exclude domain="sharedpref" path="its_prefs.xml"/>
<exclude domain="sharedpref" path="rl_prefs.xml"/>
</cloud-backup>
</data-extraction-rules>
```

**-Open the /app/manifest/AndroidManifest.xml file.**
```xml
Merge XML manifest
<application
        tools:replace = "android:fullBackupContent"
        android:allowBackup = "true"
        android:fullBackupContent="@xml/backup_rules_11"
        android:dataExtractionRules="@xml/backup_rules_12"
/>

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
<activity android:name="com.facebook.FacebookActivity"
    android:configChanges=
        "keyboard|keyboardHidden|screenLayout|screenSize|orientation"
    android:label="@string/app_name" />
<activity
    android:name="com.facebook.CustomTabActivity"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="@string/fb_login_protocol_scheme" />
    </intent-filter>
</activity>

<meta-data
    android:name="com.facebook.sdk.ApplicationId"
    android:value="@string/facebook_app_id"/>
<meta-data
    android:name="com.facebook.sdk.ClientToken"
    android:value="@string/facebook_client_token" />
<provider android:authorities="com.facebook.app.FacebookContentProvider116350609033094"
    android:name="com.facebook.FacebookContentProvider"
    android:exported="true"/>
```
USAGE GOSU LOGIN SDK
--------------------
1. Initialize configuration for GosuSDK
---
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
* intSDK: Please provide us with the SHA-1 code, from the following documentation. [click here](https://developers.google.com/android/guides/client-auth)
* Sign in with Google: Please provide us with the SHA-1 code, from the following documentation. [click here](https://developers.google.com/android/guides/client-auth)
* Sign in with Facebook: Please provide us with the hashkey code, from the following documentation. [more here](https://developers.facebook.com/docs/facebook-login/android)

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

The SDK supports tracking in-app events with enhanced analytics capabilities (ITS SDK 1.1.1). To use it, you need to implement the `GTrackingManager` module. For detailed information, refer to the code example below.

```java
// Basic tracking events
GTrackingManager.getInstance().completeRegistration("User_id");
GTrackingManager.getInstance().completeTutorial();

// Gaming-specific events  
GTrackingManager.getInstance().createNewCharacter("server_info", "char_id", "char_name");
GTrackingManager.getInstance().enterGame("user_id", "char_id", "char_name", "server_info");
GTrackingManager.getInstance().levelUp("char_id", "server_info", level);

// Custom events
JSONObject customData = new JSONObject();
customData.put("key", "value");
GTrackingManager.getInstance().trackCustomEvent("event_name", customData);
```
For detailed information on tracking events, please refer to the [Tracking Guide](./TRACKING_GUIDE.md).