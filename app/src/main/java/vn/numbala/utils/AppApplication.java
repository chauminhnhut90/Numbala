package vn.numbala.utils;

import android.app.Application;

import vn.numbala.models.LoginModel;


public class AppApplication extends Application {

    private static AppApplication instance;

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

    public String key = "";
    public LoginModel loginModel;
}
