package vn.numbala.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.Arrays;
import java.util.Locale;

import vn.numbala.services.SMSService;
import vn.numbala.utils.AppApplication;
import vn.numbala.utils.Utils;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 12/13/17 10:44 PM.
 * Project Name: Numbala
 */

public class SMSReceiver extends BroadcastReceiver {

    public static final String REQ_CODE = "REQ_CODE";
    public static final String ID = "ID";

    public static final String[] BANKs = {"VIETCOMBANK", "ACB", "SACOMBANK", "VIETINBANK", "+841637958812"};

    @Override
    public void onReceive(Context context, Intent intent) {

        if (!AppApplication.getInstance().key.isEmpty()) {
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
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

                            Intent i = new Intent(context, SMSService.class);
                            i.putExtra(REQ_CODE, 1000);
                            i.putExtra(ID, id);
                            context.getApplicationContext().startService(i);
                        }
                    }
                }
            }
        }
    }
}