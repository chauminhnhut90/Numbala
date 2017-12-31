package vn.numbala.activities;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import okhttp3.OkHttpClient;
import vn.numbala.receiver.SMSReceiver;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 11/28/17 10:22 PM.
 * Project Name: Numbala
 */

public class BaseActivity extends AppCompatActivity {

    public static final OkHttpClient client = new OkHttpClient();
    protected Context context;

    IntentFilter intentFilter;
    SMSReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
            receiver = new SMSReceiver();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != receiver)
            registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        if (null != receiver)
            unregisterReceiver(this.receiver);
        super.onPause();
    }
}
