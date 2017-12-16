package vn.numbala.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import vn.numbala.activities.BaseActivity;
import vn.numbala.models.resObj.SVResObj;
import vn.numbala.models.resObj.UpdateStatusResObj;
import vn.numbala.receiver.SMSReceiver;
import vn.numbala.utils.AppApplication;
import vn.numbala.utils.ConfigUtils;
import vn.numbala.utils.SMSUtil;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 12/16/17 10:18 AM.
 * Project Name: Numbala
 */

public class SMSService extends IntentService {

    public SMSService() {
        super("SMSService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle b = intent.getExtras();
        if (b != null) {
            int reqCode = b.getInt(SMSReceiver.REQ_CODE);
            if (reqCode == 1000) {
                // String id = b.getString(SMSReceiver.ID);
                // callAPIUpdate(id);

                SMSUtil.sendSMS(getApplicationContext(), "01637958812", "Thks for being our friends");
            }
        }
    }

    private void callAPIUpdate(String id) {

        final String key = AppApplication.getInstance().key;
        int typ = 6;

        String url = String.format("%s?key=%s&typ=%d&id=%s", ConfigUtils.DOMAIN_HTTP_API, key, typ, id);
        try {

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            BaseActivity.client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string().replace("(", "").replace(")", "");
                        SVResObj resObj = new Gson().fromJson(result, SVResObj.class);
                        if (resObj != null && resObj.status) {
                            UpdateStatusResObj model = (UpdateStatusResObj) resObj;

                            String phone = model.data.userInfo.customerPhone;
                            String message = model.data.userInfo.customerMessage;

                            SMSUtil.sendSMS(getApplicationContext(), phone, message);
                        }
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}