package vn.numbala.utils;

import android.app.Application;


public class AppApplication extends Application {

    static AppApplication instance;

    public static AppApplication getInstance() {
        if (null == instance)
            instance = new AppApplication();
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }

    private boolean isInternetConnnection = false;

    public boolean isInternetConnnection() {
        return isInternetConnnection;
    }

    public void setInternetConnnection(boolean internetConnnection) {
        isInternetConnnection = internetConnnection;
    }

}
