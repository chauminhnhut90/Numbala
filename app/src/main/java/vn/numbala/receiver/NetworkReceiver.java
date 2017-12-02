package vn.numbala.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import vn.numbala.utils.AppApplication;
import vn.numbala.utils.Utils;

public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AppApplication.getInstance().setInternetConnnection(
                Utils.checkInternetConnection(context));
    }
}
