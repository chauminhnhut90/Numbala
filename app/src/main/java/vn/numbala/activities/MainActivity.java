package vn.numbala.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.telephony.SmsMessage;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import vn.numbala.R;
import vn.numbala.adapters.ViewPagerAdapter;
import vn.numbala.fragments.BaseFragment;
import vn.numbala.fragments.ListTransactionFragment;
import vn.numbala.receiver.SMSReceiver;
import vn.numbala.services.SMSService;
import vn.numbala.utils.AppApplication;
import vn.numbala.utils.ImageUtils;

public class MainActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private TextView tvUserName;
    private ImageView avatar;

    private TextView tvDropDay, tvDropMonth, tvDropYear;
    private TextView titleDay, titleMonth, titleYear;
    private EditText etSearch;

    private int day = 0, month = 0, year = 0;
    private String query = "";

    private List<BaseFragment> fragments = new ArrayList<>();
    private BroadcastReceiver receiver;
    private IntentFilter intentFilter;

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

        titleDay = findViewById(R.id.titleDay);
        titleMonth = findViewById(R.id.titleMonth);
        titleYear = findViewById(R.id.titleYear);

        tvDropDay = findViewById(R.id.tvDropDay);
        tvDropMonth = findViewById(R.id.tvDropMonth);
        tvDropYear = findViewById(R.id.tvDropYear);

        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        tvDropDay.setTypeface(font);
        tvDropMonth.setTypeface(font);
        tvDropYear.setTypeface(font);

        findViewById(R.id.search).setOnClickListener(search);

        registerForContextMenu(titleDay);
        registerForContextMenu(titleMonth);
        registerForContextMenu(titleYear);

        registerForContextMenu(tvDropDay);
        registerForContextMenu(tvDropMonth);
        registerForContextMenu(tvDropYear);

        titleDay.setOnClickListener(menuContextListener);
        titleMonth.setOnClickListener(menuContextListener);
        titleYear.setOnClickListener(menuContextListener);

        tvDropDay.setOnClickListener(menuContextListener);
        tvDropMonth.setOnClickListener(menuContextListener);
        tvDropYear.setOnClickListener(menuContextListener);

        etSearch = findViewById(R.id.etSearch);
        avatar.setOnClickListener(listener);

        this.handleSMSComing();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(this.receiver);
        super.onPause();
    }

    private void handleSMSComing() {
        String action = getPackageName() + ".SMS_RECEIVED";
        intentFilter = new IntentFilter(action);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Click search action
                updateAllStatus();
                handleReloadPageAtIndex(viewPager.getCurrentItem());
            }
        };
    }

    private View.OnClickListener menuContextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openContextMenu(v);
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        switch (v.getId()) {
            case R.id.tvDropDay:
            case R.id.titleDay:
                inflater.inflate(R.menu.menu_day, menu);
                break;

            case R.id.tvDropMonth:
            case R.id.titleMonth:
                inflater.inflate(R.menu.menu_month, menu);
                break;

            case R.id.tvDropYear:
            case R.id.titleYear:
                inflater.inflate(R.menu.menu_year, menu);
                break;
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ProfileActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener search = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            updateAllStatus();
            handleReloadPageAtIndex(viewPager.getCurrentItem());
        }
    };

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.day_0:
                day = 0;
                break;

            case R.id.day_1:
                day = 1;
                break;

            case R.id.day_2:
                day = 2;
                break;

            case R.id.day_3:
                day = 3;
                break;

            case R.id.day_4:
                day = 4;
                break;

            case R.id.day_5:
                day = 5;
                break;

            case R.id.day_6:
                day = 6;
                break;

            case R.id.day_7:
                day = 7;
                break;

            case R.id.day_8:
                day = 8;
                break;

            case R.id.day_9:
                day = 9;
                break;

            case R.id.day_10:
                day = 10;
                break;

            case R.id.day_11:
                day = 11;
                break;

            case R.id.day_12:
                day = 12;
                break;

            case R.id.day_13:
                day = 13;
                break;

            case R.id.day_14:
                day = 14;
                break;

            case R.id.day_15:
                day = 15;
                break;

            case R.id.day_16:
                day = 16;
                break;

            case R.id.day_17:
                day = 17;
                break;

            case R.id.day_18:
                day = 18;
                break;

            case R.id.day_19:
                day = 19;
                break;

            case R.id.day_20:
                day = 20;
                break;

            case R.id.day_21:
                day = 21;
                break;

            case R.id.day_22:
                day = 22;
                break;

            case R.id.day_23:
                day = 23;
                break;

            case R.id.day_24:
                day = 24;
                break;

            case R.id.day_25:
                day = 25;
                break;

            case R.id.day_26:
                day = 26;
                break;

            case R.id.day_27:
                day = 27;
                break;

            case R.id.day_28:
                day = 28;
                break;

            case R.id.day_29:
                day = 29;
                break;

            case R.id.day_30:
                day = 30;
                break;

            case R.id.day_31:
                day = 31;
                break;

            case R.id.month_0:
                month = 0;
                break;

            case R.id.month_1:
                month = 1;
                break;

            case R.id.month_2:
                month = 2;
                break;

            case R.id.month_3:
                month = 3;
                break;

            case R.id.month_4:
                month = 4;
                break;

            case R.id.month_5:
                month = 5;
                break;

            case R.id.month_6:
                month = 6;
                break;

            case R.id.month_7:
                month = 7;
                break;

            case R.id.month_8:
                month = 8;
                break;

            case R.id.month_9:
                month = 9;
                break;

            case R.id.month_10:
                month = 10;
                break;

            case R.id.month_11:
                month = 11;
                break;

            case R.id.month_12:
                month = 12;
                break;

            case R.id.year_0:
                year = 0;
                break;

            case R.id.year_2017:
                year = 2017;
                break;

            case R.id.year_2018:
                year = 2018;
                break;

            default:
                break;
        }

        if (day != 0)
            this.titleDay.setText(String.format("%s %d", getString(R.string.day), day));
        else {
            this.titleDay.setText(getString(R.string.day));
        }

        if (month != 0)
            this.titleMonth.setText(String.format("%s %d", getString(R.string.month), month));
        else {
            this.titleMonth.setText(getString(R.string.month));
        }

        if (year != 0)
            this.titleYear.setText(String.format("%s %d", getString(R.string.year), year));
        else {
            this.titleYear.setText(getString(R.string.year));
        }

        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        fragments.clear();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        ListTransactionFragment f1 = new ListTransactionFragment();
        f1.setIndex(0);
        f1.setFilterInfo(day, month, year, query);
        adapter.addFragment(f1, getString(R.string.total));

        ListTransactionFragment f2 = new ListTransactionFragment();
        f2.setIndex(1);
        f2.setFilterInfo(day, month, year, query);
        adapter.addFragment(f2, getString(R.string.success));

        ListTransactionFragment f3 = new ListTransactionFragment();
        f3.setIndex(2);
        f3.setFilterInfo(day, month, year, query);
        adapter.addFragment(f3, getString(R.string.waiting));

        ListTransactionFragment f4 = new ListTransactionFragment();
        f4.setIndex(3);
        f4.setFilterInfo(day, month, year, query);
        adapter.addFragment(f4, getString(R.string.cancle));

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(pageChangeListener);

        fragments.add(f1);
        fragments.add(f2);
        fragments.add(f3);
        fragments.add(f4);
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            handleReloadPageAtIndex(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void handleReloadPageAtIndex(int position) {
        ListTransactionFragment fragment = (ListTransactionFragment) fragments.get(position);
        fragment.loadData();
    }

    private void updateAllStatus() {
        for (int i = 0; i < fragments.size(); i++) {
            ListTransactionFragment fragment = (ListTransactionFragment) fragments.get(i);
            fragment.setFilterInfo(day, month, year, etSearch.getText().toString());
            fragment.setFlagReload();
        }
    }
}
