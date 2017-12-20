package vn.numbala.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

    private TextView tvDropDay, tvDropMonth, tvDropYear;

    private int day = 0, month = 0, year = 0;

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

        tvDropDay = findViewById(R.id.tvDropDay);
        tvDropMonth = findViewById(R.id.tvDropMonth);
        tvDropYear = findViewById(R.id.tvDropYear);

        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        tvDropDay.setTypeface(font);
        tvDropMonth.setTypeface(font);
        tvDropYear.setTypeface(font);

        findViewById(R.id.search).setOnClickListener(search);

        View day = findViewById(R.id.day);
        View month = findViewById(R.id.month);
        View year = findViewById(R.id.year);

        registerForContextMenu(day);
        registerForContextMenu(month);
        registerForContextMenu(year);

        day.setOnClickListener(menuContextListener);
        month.setOnClickListener(menuContextListener);
        year.setOnClickListener(menuContextListener);
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
            case R.id.day:
                inflater.inflate(R.menu.menu_day, menu);
                break;

            case R.id.month:
                inflater.inflate(R.menu.menu_month, menu);
                break;

            case R.id.year:
                inflater.inflate(R.menu.menu_year, menu);
                break;
        }
    }

    private View.OnClickListener search = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // return super.onContextItemSelected(item);

        switch (item.getItemId()) {
            case R.id.day_1:
                day = 1;
                return true;

            case R.id.day_2:
                day = 2;
                return true;

            case R.id.day_3:
                day = 3;
                return true;

            case R.id.day_4:
                day = 4;
                return true;

            case R.id.day_5:
                day = 5;
                return true;

            case R.id.day_6:
                day = 6;
                return true;

            case R.id.day_7:
                day = 7;
                return true;

            case R.id.day_8:
                day = 8;
                return true;

            case R.id.day_9:
                day = 9;
                return true;

            case R.id.day_10:
                day = 10;
                return true;

            case R.id.day_11:
                day = 11;
                return true;

            case R.id.day_12:
                day = 12;
                return true;

            case R.id.day_13:
                day = 13;
                return true;

            case R.id.day_14:
                day = 14;
                return true;

            case R.id.day_15:
                day = 15;
                return true;

            case R.id.day_16:
                day = 16;
                return true;

            case R.id.day_17:
                day = 17;
                return true;

            case R.id.day_18:
                day = 18;
                return true;

            case R.id.day_19:
                day = 19;
                return true;

            case R.id.day_20:
                day = 20;
                return true;

            case R.id.day_21:
                day = 21;
                return true;

            case R.id.day_22:
                day = 22;
                return true;

            case R.id.day_23:
                day = 23;
                return true;

            case R.id.day_24:
                day = 24;
                return true;

            case R.id.day_25:
                day = 25;
                return true;

            case R.id.day_26:
                day = 26;
                return true;

            case R.id.day_27:
                day = 27;
                return true;

            case R.id.day_28:
                day = 28;
                return true;

            case R.id.day_29:
                day = 29;
                return true;

            case R.id.day_30:
                day = 30;
                return true;

            case R.id.month_1:
                month = 1;
                return true;

            case R.id.month_2:
                month = 2;
                return true;

            case R.id.month_3:
                month = 3;
                return true;

            case R.id.month_4:
                month = 4;
                return true;

            case R.id.month_5:
                month = 5;
                return true;

            case R.id.month_6:
                month = 6;
                return true;

            case R.id.month_7:
                month = 7;
                return true;

            case R.id.month_8:
                month = 8;
                return true;

            case R.id.month_9:
                month = 9;
                return true;

            case R.id.month_10:
                month = 10;
                return true;

            case R.id.month_11:
                month = 11;
                return true;

            case R.id.month_12:
                month = 12;
                return true;

            case R.id.year_2017:
                year = 2017;
                return true;

            case R.id.year_2018:
                year = 2018;
                return true;

            default:
                return false;
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        ListTransactionFragment f1 = new ListTransactionFragment();
        f1.setIndex(0);
        f1.setDMY(day, month, year);
        adapter.addFragment(f1, getString(R.string.total));

        ListTransactionFragment f2 = new ListTransactionFragment();
        f2.setIndex(1);
        f1.setDMY(day, month, year);
        adapter.addFragment(f2, getString(R.string.success));

        ListTransactionFragment f3 = new ListTransactionFragment();
        f3.setIndex(2);
        f1.setDMY(day, month, year);
        adapter.addFragment(f3, getString(R.string.waiting));

        ListTransactionFragment f4 = new ListTransactionFragment();
        f4.setIndex(3);
        f1.setDMY(day, month, year);
        adapter.addFragment(f4, getString(R.string.cancle));

        viewPager.setAdapter(adapter);
    }
}
