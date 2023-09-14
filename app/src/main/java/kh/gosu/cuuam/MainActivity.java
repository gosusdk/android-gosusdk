package kh.gosu.cuuam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.game.gsdk.inteface.IGameOauthListener;
import com.game.gsdk.inteface.IGamePaymentListener;
import com.game.gsdk.inteface.OnSingleClickListener;
import com.game.gsdk.object.GameItemIAPObject;
import com.game.gsdk.utils.GameConstant;
import com.game.gsdk.utils.GosuSDK;
import com.game.gstracking.GTrackingManager;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements IGameOauthListener {
    private Button btnDangNhap;
    private Button btnTTTK;
    private Button btnDSITEM;
    private Button btnTTITEM1;
    private Button btnDeleteAccount;
    private Button btnDangXuat;

    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Context context = getApplicationContext();
        mActivity = this;
        Utility.printKeyHash(this);
        GosuSDK.getInstance().setOauthListener(this);
        GosuSDK.getInstance().initSdk(this);
    }

    protected void callTrackingExample()
    {
        GTrackingManager.getInstance().trackingStartTrial();
        GTrackingManager.getInstance().trackingTutorialCompleted();
        GTrackingManager.getInstance().doneNRU(
                "server_id",
                "role_id",
                "Role Name"
        );
        String abc = null;
        GTrackingManager.getInstance().doneNRU(
                abc,
                abc,
                abc
        );
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_id", "1");
            GTrackingManager.getInstance().trackingEvent("level_20", jsonObject);
        } catch (Exception ignored) {}

        /* custom event */
        GTrackingManager.getInstance().trackingEvent("level_20");
        GTrackingManager.getInstance().trackingEvent("level_20", abc);
        GTrackingManager.getInstance().trackingEvent("level_20", "{\"test\":\"abc\"}");
        GTrackingManager.getInstance().trackingEvent("level_20", "{\"customer_id\":\"12345\"}");
    }

    public void initView() {
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangNhap.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {
                GosuSDK.getInstance().showSignIn();
            }
        });


        btnDSITEM = (Button) findViewById(R.id.btnDSITEM);
        btnDSITEM.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                for(int i = 0; i < GameConstant.iap_product_ids.size(); i++){
                    Log.d("TAG_ITEM", GameConstant.iap_product_ids.get(i)+"");
                }
            }
        });


        btnDeleteAccount = (Button) findViewById(R.id.btnDeleteAccount);
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GosuSDK.getInstance().deleteAccount();
            }
        });



        btnTTITEM1 = (Button) findViewById(R.id.btnTTITEM1);
        btnTTITEM1.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {

                if(GameConstant.iap_product_ids.size() <= 0) return;

                String productID = "com.flashpoint.nemo.100kc"; //GameConstant.iap_product_ids.get(0);
                String mProductName = "Mua gói 100KNB";
                String amount = "22000";
                String orderID = randomString(10); //random string your
                String serverID       = "S1";
                String characterID    = "Character_ID";
                String extraInfo    = "";

                GameItemIAPObject gosuItemIAPObject = new GameItemIAPObject(productID, mProductName, orderID, amount, serverID, characterID, extraInfo);


                GosuSDK.getInstance().showIAP(gosuItemIAPObject, new IGamePaymentListener() {
                    @Override
                    public void onPaymentSuccess(String message) {
                        Log.d("T123", message);
                    }

                    @Override
                    public void onPaymentError(String message) {
                        Log.d("T123", message);

                    }
                });
            }
        });

        btnDangXuat = (Button) findViewById(R.id.btnDangXuat);
        btnDangXuat.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View var1) {
                GosuSDK.getInstance().logout();
            }
        });

        btnDangNhap.setVisibility(View.VISIBLE);
        btnDSITEM.setVisibility(View.GONE);
        btnTTITEM1.setVisibility(View.GONE);
        btnDangXuat.setVisibility(View.GONE);
        btnDeleteAccount.setVisibility(View.GONE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        GosuSDK.getInstance().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private String randomString(int n){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    @Override
    public void onLoginSuccess(String UserId, String UserName, String accesstoken) {
        btnDangNhap.setVisibility(View.GONE);
        btnDSITEM.setVisibility(View.VISIBLE);
        btnTTITEM1.setVisibility(View.VISIBLE);
        btnDangXuat.setVisibility(View.VISIBLE);
        btnDeleteAccount.setVisibility(View.VISIBLE);
        callTrackingExample();
    }

    @Override
    public void onLogout() {
        btnDangNhap.setVisibility(View.GONE);
        btnDSITEM.setVisibility(View.GONE);
        btnTTITEM1.setVisibility(View.GONE);
        btnDangXuat.setVisibility(View.GONE);
        btnDeleteAccount.setVisibility(View.GONE);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onDeleteAccount(String status) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CharSequence text = "Account deleted successfully!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(mActivity /* MyActivity */, text, duration);
                toast.show();
            }
        });
    }
}