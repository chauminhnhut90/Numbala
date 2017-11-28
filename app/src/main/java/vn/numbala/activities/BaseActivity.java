package vn.numbala.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 11/28/17 10:22 PM.
 * Project Name: Numbala
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    }
}
