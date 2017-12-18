package vn.numbala.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import vn.numbala.R;
import vn.numbala.adapters.ViewPagerAdapter;
import vn.numbala.fragments.ListTransactionFragment;
import vn.numbala.utils.AppApplication;
import vn.numbala.utils.ImageUtils;
import vn.numbala.utils.SMSUtil;

public class MainActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private TextView tvUserName;
    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(null);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_success_tab);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_wait_tab);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_cancle_tab);

        tvUserName = findViewById(R.id.tvUserName);
        avatar = findViewById(R.id.avatar);

        AppApplication application = AppApplication.getInstance();
        tvUserName.setText(application.loginModel.Name);

        int size = (int) getResources().getDimension(R.dimen.avatar_size);
        ImageUtils.loadCircle(this, avatar, application.loginModel.Avatar, size, size);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        ListTransactionFragment f1 = new ListTransactionFragment();
        f1.setIndex(0);
        adapter.addFragment(f1, getString(R.string.total));

        ListTransactionFragment f2 = new ListTransactionFragment();
        f2.setIndex(1);
        adapter.addFragment(f2, getString(R.string.success));

        ListTransactionFragment f3 = new ListTransactionFragment();
        f3.setIndex(2);
        adapter.addFragment(f3, getString(R.string.waiting));

        ListTransactionFragment f4 = new ListTransactionFragment();
        f4.setIndex(3);
        adapter.addFragment(f4, getString(R.string.cancle));

        viewPager.setAdapter(adapter);
    }
}
