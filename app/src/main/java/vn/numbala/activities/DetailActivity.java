package vn.numbala.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.numbala.R;
import vn.numbala.models.TransactionModel;
import vn.numbala.utils.Utils;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 12/2/17 4:23 PM.
 * Project Name: Numbala
 */

public class DetailActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_MODEL = "KEY_MODEL";
    private TextView tvWebsite, tvId, tvTransdate, tvPrice, tvFullName, tvPhone, tvEmail, tvLink, tvAddress, tvNote, title;
    private ImageView image;
    private View container, textview;
    private TransactionModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        findViewById(R.id.close).setOnClickListener(this);

        container = findViewById(R.id.container);
        textview = findViewById(R.id.textview);

        tvWebsite = findViewById(R.id.tvWebsite);
        tvId = findViewById(R.id.tvId);
        tvTransdate = findViewById(R.id.tvTransdate);
        tvPrice = findViewById(R.id.tvPrice);
        tvFullName = findViewById(R.id.tvFullName);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvLink = findViewById(R.id.tvLink);
        tvAddress = findViewById(R.id.tvAddress);
        tvNote = findViewById(R.id.tvNote);
        image = findViewById(R.id.image);
        title = findViewById(R.id.title);

        Bundle b = getIntent().getExtras();
        if (null != b) {
            this.model = (TransactionModel) b.getSerializable(KEY_MODEL);
        }

        showUI();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                this.finish();
                break;
        }
    }

    private void showUI() {
        if (this.model != null) {
            this.tvWebsite.setText(model.Web);
            this.tvId.setText(model.ID.replace("ID", ""));
            this.tvTransdate.setText(model.Date_created);
            this.tvPrice.setText(Utils.format(Long.parseLong(model.Price)) + " đ");

            this.tvFullName.setText(model.Customer_name);
            this.tvPhone.setText(model.Customer_phone);
            this.tvEmail.setText(model.Customer_email);
            this.setAddress(model.Customer_add);
            this.setNote(model.Note);

            if (model.Status_num.equals("1")) {
                this.container.setBackgroundResource(R.drawable.drawable_bg_detail_header_fail);
                this.textview.setBackgroundColor(ContextCompat.getColor(this, R.color.detail_header_fail));
                this.title.setText(getString(R.string.trans_waiting));
                this.image.setImageResource(R.drawable.ic_fail_2);
            } else {
                this.container.setBackgroundResource(R.drawable.drawable_bg_detail_header);
                this.textview.setBackgroundColor(ContextCompat.getColor(this, R.color.detail_header));
                this.title.setText(getString(R.string.trans_success));
                this.image.setImageResource(R.drawable.ic_success_2);
            }
        }
    }

    @SuppressWarnings("unused")
    private void setLink(String link) {
        SpannableString content = new SpannableString(link);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvLink.setText(content);
    }

    private void setNote(String note) {
        SpannableString spanString = new SpannableString(getString(R.string.note) + "   " + note);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, 10, 0);
        spanString.setSpan(new StyleSpan(Typeface.ITALIC), 10, spanString.length(), 0);
        tvNote.setText(spanString);
    }

    private void setAddress(String address) {
        tvAddress.setText(getString(R.string.address) + "   " + address);
    }
}
