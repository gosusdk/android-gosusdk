package kh.gosu.cuuam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.game.gsdk.inteface.IGameOauthListener;
import com.game.gsdk.inteface.IGamePaymentListener;
import com.game.gsdk.object.GameItemIAPObject;
import com.game.gsdk.utils.GameConstant;
import com.game.gsdk.utils.GosuSDK;
import com.game.gstracking.GTrackingManager;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, IGameOauthListener {
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mActivity = this;
    }
    HashMap<Integer, Integer> buttonMap = new HashMap<Integer, Integer>() {
        {
            put(R.id.btnDangNhap, 0);
            put(R.id.btnDSITEM, 1);
            put(R.id.btnDeleteAccount, 1);
            put(R.id.btnTTITEM1, 1);
            put(R.id.btnDangXuat, 1);
        }
    };
    public void initView() {
        GosuSDK.getInstance().setOauthListener(this);
        GosuSDK.getInstance().setOauthListener(new IGameOauthListener() {
            @Override
            public void onLoginSuccess(String s, String s1, String s2) {

            }

            @Override
            public void onLogout() {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onDeleteAccount(String s) {

            }
        });
        GosuSDK.getInstance().initSdk(this);
        GosuSDK.getInstance().onlyInitSdk(this);
        for(Map.Entry<Integer, Integer> entry : buttonMap.entrySet()) {
            findViewById(entry.getKey()).setOnClickListener(this);
        }
        setVisibleButton(false /* isLogedIn */);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDSITEM:
                String productList = "";
                for(String productId : GameConstant.iap_product_ids) {
                    if (!productList.isEmpty()) productList += "\n";
                    productList += productId;
                }
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Product List");
                alert.setMessage(productList);
                alert.create();
                alert.show();
                break;
            case R.id.btnDangXuat:
                GosuSDK.getInstance().logout();
                break;
            case R.id.btnDeleteAccount:
                GosuSDK.getInstance().deleteAccount();
                break;
            case R.id.btnTTITEM1:
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
                        showMessage(message);
                    }

                    @Override
                    public void onPaymentError(String message) {
                        showMessage(message);
                    }
                });
                break;
            case R.id.btnDangNhap:
            default:
                GosuSDK.getInstance().showSignIn();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void onLoginSuccess(String UserId, String UserName, String accesstoken) {
        setVisibleButton(true /* isLogedIn */);
        callTrackingExample();
        showMessage("Login success: " + UserName);
    }

    @Override
    public void onLogout() {
        setVisibleButton(false /* isLogedIn */);
        showMessage("LogOut success: ");
    }

    @Override
    public void onError() {
        showMessage("OnError");
    }

    @Override
    public void onDeleteAccount(String status) {
        showMessage("Account deleted successfully!");
    }
    public void showMessage(String message)
    {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CharSequence text = message;
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(mActivity /* MyActivity */, text, duration);
                toast.show();
            }
        });
    }

    public void setVisibleButton(boolean isLoggedIn)
    {
        try {
            int value = isLoggedIn ? 1 : 0;
            for(Map.Entry<Integer, Integer> entry : buttonMap.entrySet()) {
                if (entry.getValue() == value) {
                    findViewById(entry.getKey()).setVisibility(View.VISIBLE);
                } else {
                    findViewById(entry.getKey()).setVisibility(View.GONE);
                }
            }
        } catch (Exception ignored) {
        }
    }
}