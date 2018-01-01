package vn.numbala.activities;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import vn.numbala.R;
import vn.numbala.models.JsonIpModel;
import vn.numbala.models.resObj.LoginResObj;
import vn.numbala.models.resObj.SVResObj;
import vn.numbala.utils.AppApplication;
import vn.numbala.utils.ConfigUtils;
import vn.numbala.utils.Utils;

public class LoginActivity extends BaseActivity {

    private static final int MY_PERMISSIONS = 1000;
    private EditText etEmail, etPass;
    private String imei = "", ip = "";
    private boolean neverAskPermission = false;

    private String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);

        // etEmail.setText("khachhang@yahoo.com");
        // etPass.setText("123qwe");
        findViewById(R.id.btLogin).setOnClickListener(this.listener);

        this.requestPermission();

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Utils.hasPermissions(context, permissions)) {
                if (check())
                    // login();
                    getIP();
            } else {
                requestPermission();
            }
        }
    };

    private void getIP(){
        Utils.showProgressDialog(context);
        this.getIpAddress();
        this.getIpAddressV2();
    }

    private boolean check() {
        if (this.etEmail.getText().toString().isEmpty() || this.etPass.getText().toString().isEmpty()) {
            Utils.showToast(context, "Email or Password is empty ");
            return false;
        }

        this.getIMEI();
        if (this.imei.isEmpty()) {
            Utils.showToast(context, "IMEI was be empty");
            return false;
        }

//        if (this.ip.isEmpty()) {
//            Utils.showToast(context, "IP Address was be empty");
//            return false;
//        }
        return true;
    }

    private void login() {
        // Utils.showProgressDialog(context);

        String email = this.etEmail.getText().toString();
        String pass = this.etPass.getText().toString();

        String s = email + pass + "@7DFj2Eai52%#sh";
        s = Utils.md5(s);
        s = Utils.md5(s);
        s = Utils.md5(s);
        s = Utils.md5(s);
        Utils.logInfo("Pass MD5 4 times: " + s);

        final String key = s;
        int typ = 1;
        String dev = android.os.Build.MODEL; // Utils.getDeviceName();

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

                            // Cache KEY
                            AppApplication application = AppApplication.getInstance();
                            application.key = key;
                            application.imei = imei;
                            application.loginModel = loginResObj.data;

                            startActivity(new Intent(context, MainActivity.class));
                            finish();
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
        if (Build.VERSION.SDK_INT >= 23 && this.permissions.length != 0) {
            if (!Utils.hasPermissions(context, permissions)) {
                if (!neverAskPermission)
                    ActivityCompat.requestPermissions((Activity) context, permissions, MY_PERMISSIONS);
                else {
                    Utils.showMessageOKCancel(context, "Go to Setting and turn all permissions on first", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS: {
                if (grantResults.length > 0) {

                    boolean a = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean b = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean c = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (a && b && c) {
                        Utils.showToast(context, "All permissions has been granted");
                    } else {
                        if (Utils.shouldShowRequestPermissionRationale(context, permissions)) {
                            Utils.showMessageOKCancel(context, "Numbala need to access Phone call and SMS message", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermission();
                                }
                            });
                        } else {
                            // Handle Never Ask Again
                            neverAskPermission = true;
                        }
                    }
                }
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
                        if (resObj != null && ip.isEmpty()) {
                            ip = resObj.ip;
                            login();
                        }
                    } else {
                        Utils.showToast(context, "Can not find ip address. Re-try later");
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getIpAddressV2() {
        try {

            Request request = new Request.Builder()
                    .url("https://api.ipify.org/?format=json")
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
                        if (resObj != null && ip.isEmpty()) {
                            ip = resObj.ip;
                            login();
                        }
                    } else {
                        Utils.showToast(context, "Can not find ip address. Re-try later");
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getIMEI() throws SecurityException {
        TelephonyManager tm = (TelephonyManager)
                getSystemService(this.TELEPHONY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            this.imei = tm.getMeid();
        } else {
            this.imei = tm.getDeviceId();
        }
    }
}
