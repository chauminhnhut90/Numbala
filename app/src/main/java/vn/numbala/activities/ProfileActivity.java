package vn.numbala.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.numbala.R;
import vn.numbala.models.LoginModel;
import vn.numbala.utils.AppApplication;
import vn.numbala.utils.ImageUtils;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 12/2/17 4:23 PM.
 * Project Name: Numbala
 */

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvFullName, tvPhone, tvEmail, tvCreateDate;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViewById(R.id.close).setOnClickListener(this);

        tvFullName = findViewById(R.id.tvFullName);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvCreateDate = findViewById(R.id.tvCreateDate);
        image = findViewById(R.id.image);


        LoginModel model = AppApplication.getInstance().loginModel;
        tvFullName.setText(model.Name);
        tvPhone.setText(model.Phone);
        tvEmail.setText(model.Email);
        tvCreateDate.setText(model.Date_created);

        int size = (int) getResources().getDimension(R.dimen.avatar_size);
        ImageUtils.loadCircle(this, image, model.Avatar, size, size);

        findViewById(R.id.tvLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppApplication.getInstance().reset();
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
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
