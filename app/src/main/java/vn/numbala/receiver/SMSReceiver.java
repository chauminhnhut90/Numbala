package vn.numbala.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import vn.numbala.services.SMSService;
import vn.numbala.utils.AppApplication;
import vn.numbala.utils.SharePrefUtils;
import vn.numbala.utils.Utils;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 12/13/17 10:44 PM.
 * Project Name: Numbala
 */

public class SMSReceiver extends BroadcastReceiver {

    public static final String REQ_CODE = "REQ_CODE";
    public static final String ID = "ID";

    public static final String[] BANKs = {"VIETCOMBANK", "ACB", "SACOMBANK", "VIETINBANK", "+841674035635", "+841686689987"};

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals("android.provider.Telephony.SMS_RECEIVED")) {
            if (!AppApplication.getInstance().key.isEmpty()) {

                Bundle b = intent.getExtras();
                final Object[] pdusObj = (Object[]) b.get("pdus");

                String message = "";
                String senderNum = "";
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage
                            .createFromPdu((byte[]) pdusObj[i]);

                    senderNum = currentMessage.getDisplayOriginatingAddress();
                    message += currentMessage.getDisplayMessageBody();
                }

                if (!senderNum.isEmpty() && !message.isEmpty()) {

                    senderNum = senderNum.toLowerCase(Locale.getDefault());
                    message = message.toUpperCase(Locale.getDefault());

                    if (Arrays.asList(BANKs).contains(senderNum)) {
                        if (message.contains("NUM_")) {
                            int start = message.indexOf("NUM_");
                            int end = message.indexOf(" ", start);

                            if (start != -1 && end == -1) {
                                end = message.length();
                            }

                            String id = message.substring(start, end);
                            this.appendId(context, id);

                            if (Utils.isNetworkConnected(context)) {
                                this.sendAll(context);
                            }
                        }
                    }
                }

            }
        } else if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            if (Utils.isNetworkConnected(context)) {
                this.sendAll(context);
            }
        }
    }

    private void send(Context context, String id) {
        Intent i = new Intent(context, SMSService.class);
        i.putExtra(REQ_CODE, 1000);
        i.putExtra(ID, id);
        context.getApplicationContext().startService(i);
    }

    private void sendAll(Context context) {
        String ids = SharePrefUtils.getInstance(context).read(SharePrefUtils.IDs);
        if (ids != null && !ids.isEmpty()) {
            List<String> list = Arrays.asList(ids.split("-"));
            for (int i = list.size() - 1; i >= 0; i--) {
                this.send(context, list.get(i));
            }
            SharePrefUtils.getInstance(context).clear();
        }
    }

    private void appendId(Context context, String id) {
        String ids = SharePrefUtils.getInstance(context).read(SharePrefUtils.IDs);
        if(null == ids)
            ids = "";
        ids += String.format("%s-", id);
        SharePrefUtils.getInstance(context).save(SharePrefUtils.IDs, ids);
    }
}