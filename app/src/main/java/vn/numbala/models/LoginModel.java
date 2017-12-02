package vn.numbala.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 11/29/17 10:17 PM.
 * Project Name: Numbala
 */

public class LoginModel {

    @SerializedName("Name")
    public String Name;
    @SerializedName("Sex")
    public String Sex;
    @SerializedName("Sex_text")
    public String Sex_text;
    @SerializedName("Birthday")
    public String Birthday;
    @SerializedName("Email")
    public String Email;
    @SerializedName("Phone")
    public String Phone;
    @SerializedName("Avatar")
    public String Avatar;
    @SerializedName("Date_created")
    public String Date_created;
    @SerializedName("Lock_user")
    public String Lock_user;
    @SerializedName("Phone_Device")
    public List<Phone_Device> Phone_Device;

    public static class Phone_Device {
        @SerializedName("Imei")
        public String Imei;
        @SerializedName("Device")
        public String Device;
        @SerializedName("IP")
        public String IP;
        @SerializedName("Login_date_created")
        public String Login_date_created;
        @SerializedName("Login_date_update")
        public String Login_date_update;
        @SerializedName("Lock_Device")
        public String Lock_Device;
        @SerializedName("Lock_Device_text")
        public String Lock_Device_text;
    }
}
