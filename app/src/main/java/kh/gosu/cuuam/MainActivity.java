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
        GosuSDK.getInstance().initSdk(this);
        GosuSDK.getInstance().onlyInitSdk(this);
        for(Map.Entry<Integer, Integer> entry : buttonMap.entrySet()) {
            findViewById(entry.getKey()).setOnClickListener(this);
        }
        setVisibleButton(false /* isLogedIn */);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDSITEM) {
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
        } else if (v.getId() == R.id.btnDangXuat) {
            GosuSDK.getInstance().logout();
        } else if (v.getId() == R.id.btnDeleteAccount) {
            GosuSDK.getInstance().deleteAccount();
        } else if (v.getId() == R.id.btnTTITEM1) {
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
        } else if (v.getId() == R.id.btnDangNhap) {
            GosuSDK.getInstance().showSignIn();
        } else {
            GosuSDK.getInstance().showSignIn();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void callTrackingExample()
    {
        GTrackingManager.getInstance().createNewCharacter("server_test01", "role_test01", "role_name_test01");
        GTrackingManager.getInstance().enterGame("user_idtest01", "role_test01", "role_name_test01", "server_test01");
        GTrackingManager.getInstance().startTutorial("user_idtest01", "role_test01", "role_name_test01", "server_test01");
        GTrackingManager.getInstance().completeTutorial("user_idtest01", "role_test01", "role_name_test01", "server_test01");
        GTrackingManager.getInstance().checkout("order_test01", "pro_01", "1", "VND", "customer_test01");
        GTrackingManager.getInstance().purchase("order_test01", "pro_01", "1", "VND", "customer_test01");
        GTrackingManager.getInstance().levelUp("user_idtest01", "role_test01", 10);
        GTrackingManager.getInstance().vipUp("user_idtest01", "role_test01", 3);
        GTrackingManager.getInstance().useItem("user_idtest01", "role_test01", "item_test01", "itemid_test01", 1);
        GTrackingManager.getInstance().trackActivityResult("user_idtest01", "role_test01", "item_test01", "activity_01", "Passed");
        JSONObject object = new JSONObject();
        try {
            object.put("key_1", "1234");
            object.put("key_2", "test");
        } catch (Exception e) {
        }
        GTrackingManager.getInstance().trackCustomEvent("event_custom_toantest", object);
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