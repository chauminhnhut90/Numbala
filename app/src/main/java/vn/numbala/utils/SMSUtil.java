package vn.numbala.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.List;

import vn.numbala.models.SMSData;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 12/16/17 9:04 AM.
 * Project Name: Numbala
 */

public class SMSUtil {

    public static void sendSMS(Context context, String number, String message) {
        if (number.equals("") || message.equals(""))
            return;

        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
                new Intent("SMS_SENT"), 0);

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number, null, message, sentPI, null);
    }

    public static void readAllSMS(Context context) {
        List<SMSData> SMSs = new ArrayList<>();

        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c = context.getContentResolver().query(uri, null, null, null, null);
        ((Activity) context).startManagingCursor(c);

        if (c.moveToFirst()) {
            for (int i = 0; i < c.getCount(); i++) {
                SMSData sms = new SMSData();
                sms.body = (c.getString(c.getColumnIndexOrThrow("body")).toString());
                sms.address = (c.getString(c.getColumnIndexOrThrow("address")).toString());

                if (sms.address.equals("Vietcombank")) {
                    Utils.logInfo(sms.body);
                    SMSs.add(sms);
                }

                c.moveToNext();
            }
        }
        c.close();
    }
}
