package vn.numbala.activities;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import vn.numbala.R;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 12/2/17 4:23 PM.
 * Project Name: Numbala
 */

public class DetailActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_detail);

        findViewById(R.id.close).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                this.finish();
                break;
        }
    }
}
