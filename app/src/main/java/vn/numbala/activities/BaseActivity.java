package vn.numbala.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 11/28/17 10:22 PM.
 * Project Name: Numbala
 */

public class BaseActivity extends AppCompatActivity {

    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        getSupportActionBar().hide();
    }
}
