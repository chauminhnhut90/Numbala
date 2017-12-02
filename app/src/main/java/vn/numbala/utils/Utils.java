package vn.numbala.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.numbala.R;

public class Utils {


    static ProgressDialog progress;
    private static AtomicInteger mOpenCounter = new AtomicInteger();

    public static void init(Context context) {

        AppApplication.getInstance().setInternetConnnection(
                Utils.checkInternetConnection(context
                        .getApplicationContext()));
    }


    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }


    public static boolean isNetWorkAvailable() {
        return AppApplication.getInstance().isInternetConnnection();
    }

    public static JSONObject buildJson(Map<String, Object> params) {
        JSONObject json = new JSONObject();
        try {
            if (params != null && params.entrySet().size() > 0) {
                for (Object key : params.keySet()) {
                    json.putOpt(key.toString(), params.get(key));
                }
            }
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }
        return json;
    }

    public static ProgressDialog showProgressDialog(Context context) {

        if (mOpenCounter.incrementAndGet() == 1) {

            progress = new ProgressDialog(context);
            progress.show();
            progress.setCancelable(true);
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // progress.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            progress.setCanceledOnTouchOutside(false);
            progress.setContentView(R.layout.layout_custom_progress_dialog);
        }

        return progress;
    }

    public static void closeProgressDialog() {
        if (progress != null) {
            if (mOpenCounter.decrementAndGet() == 0) {
                progress.dismiss();
            }

        }
    }

    //END

    /*TOAST & LOG*/
    public static void showToast(final Context context, final String message) {
        if (!TextUtils.isEmpty(message)) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static void showToastNoInternetConnection(Context context) {
        showToast(context, context.getString(R.string.no_internet_connection));
    }

    public static void showToastInternetConnectionTimeout(Context context) {
        showToast(context, context.getString(R.string.server_not_found));
    }

    public static void logInfo(String message) {
        if (!TextUtils.isEmpty(message))
            Log.i("", message);
    }


    public static void logError(String message) {
        if (!TextUtils.isEmpty(message))
            Log.e("", message);
    }

    //END

    /*
    * KEYBOARD
    * */

    public static void showKeyboard(Context context) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

    }

    public static void hideKeyboard(Context context, View view) {

        InputMethodManager inputManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

    }

    public static void hideKeyboard(Context context) {

        Activity activity = (Activity) context;
        View view = activity.getCurrentFocus();
        if (null != view) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void setTextIntoTextView(TextView textView, String text) {
        if (textView != null && !TextUtils.isEmpty(text)) {
            textView.setText(text);
        }
    }

    public static String formatDisplayDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(ConfigUtils.FORMAT_DISPLAY_DATE);
        return format.format(date);
    }

    public static Intent getFBProfileIntent(Context context, String id) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://profile/" + id));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/" + id));
        }
    }

    public static Intent getFBPostIntent(Context context, String id) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://post/" + id));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/" + id));
        }
    }

    public static Intent getFBPageIntent(Context context, String id) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://page/" + id));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/"));
        }
    }

    public static void setFullscreenBg(Activity context) {
        context.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isPhoneValid(String phone) {
        boolean isValid = true;

        if (phone.length() != 10 && phone.length() != 11) {
            isValid = false;
        } else {
            if (phone.length() == 10 && !phone.startsWith("09")) {
                isValid = false;
            } else if (phone.length() == 11 && !phone.startsWith("01")) {
                isValid = false;
            }
        }

        return isValid;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDeviceName(){
        // android.os.Build.MODEL
        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        return myDevice.getName();
    }

    public static String getLocalIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }

    public static String format(long number){
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formatted = formatter.format(number);
        return formatted.replace(",",".");
    }
}
