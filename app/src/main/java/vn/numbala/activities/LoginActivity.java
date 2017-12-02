package vn.numbala.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import vn.numbala.R;
import vn.numbala.models.JsonIpModel;
import vn.numbala.models.resObj.LoginResObj;
import vn.numbala.models.resObj.SVResObj;
import vn.numbala.utils.ConfigUtils;
import vn.numbala.utils.Utils;

public class LoginActivity extends BaseActivity {

    private static final int PERMISSION_READ_STATE = 1000;
    private EditText etEmail, etPass;
    private final OkHttpClient client = new OkHttpClient();
    private String imei, ip = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);

        etEmail.setText("khachhang@yahoo.com");
        etPass.setText("123qwe");
        findViewById(R.id.btLogin).setOnClickListener(this.listener);

        this.requestPermission();
        this.getIpAddress();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            login();
        }
    };

    private void login() {
        Utils.showProgressDialog(context);

        String email = this.etEmail.getText().toString();
        String pass = this.etPass.getText().toString();

        String s = email + pass + "@7DFj2Eai52%#sh";
        s = Utils.md5(s);
        s = Utils.md5(s);
        s = Utils.md5(s);
        s = Utils.md5(s);
        Utils.logInfo("Pass MD5 4 times: " + s);

        String key = "f444a3f22ced1ea4296b1a049f17fe78";
        int typ = 1;
        String dev = Utils.getDeviceName();

        String url = String.format("%s?key=%s&typ=%d&imei=%s&dev=%s&ip=%s", ConfigUtils.DOMAIN_HTTP_API, key, typ, imei, dev, ip);
        try {

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Utils.closeProgressDialog();
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string().replace("(", "").replace(")", "");
                        SVResObj resObj = new Gson().fromJson(result, SVResObj.class);
                        if (resObj != null && resObj.status) {
                            LoginResObj loginResObj = new Gson().fromJson(result, LoginResObj.class);
                            Utils.logInfo(loginResObj.toString());
                        }
                        Utils.showToast(context, resObj.message);
                    } else {
                        Utils.showToast(context, "Something wrong...!!!");
                    }
                    Utils.closeProgressDialog();
                }
            });
        } catch (Exception ex) {
            Utils.closeProgressDialog();
            ex.printStackTrace();
        }
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);
        } else {

            TelephonyManager tm = (TelephonyManager)
                    getSystemService(this.TELEPHONY_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                this.imei = tm.getMeid();
            } else {
                this.imei = tm.getDeviceId();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_STATE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.requestPermission();
                } else {
                    Utils.showToast(context, "Phone permission was be deny");
                }
                return;
            }
        }
    }

    private void getIpAddress() {
        try {

            Request request = new Request.Builder()
                    .url("https://jsonip.com/")
                    .get()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string().replace("(", "").replace(")", "");
                        JsonIpModel resObj = new Gson().fromJson(result, JsonIpModel.class);
                        if (resObj != null) {
                            ip = resObj.ip;
                        }
                    } else {
                        Utils.showToast(context, "Something wrong...!!!");
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
