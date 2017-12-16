package vn.numbala.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import vn.numbala.utils.Utils;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 12/13/17 10:44 PM.
 * Project Name: Numbala
 */

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle b = intent.getExtras();
            final Object[] pdusObj = (Object[]) b.get("pdus");
            for (int i = 0; i < pdusObj.length; i++) {
                SmsMessage currentMessage = SmsMessage
                        .createFromPdu((byte[]) pdusObj[i]);
                String senderNum = currentMessage
                        .getDisplayOriginatingAddress();
                String message = currentMessage.getDisplayMessageBody();

                String s = "Sender: " + senderNum + "\nMessage: " + message;
                Utils.logInfo(s);
                Utils.showToast(context, s);
            }
        }
    }
}