package vn.numbala.models;

import com.google.gson.annotations.SerializedName;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 12/2/17 12:53 PM.
 * Project Name: Numbala
 */

public class TransactionModel {

    @SerializedName("ID")
    public String ID;
    @SerializedName("Web")
    public String Web;
    @SerializedName("Price")
    public String Price;
    @SerializedName("Note")
    public String Note;
    @SerializedName("Date_created")
    public String Date_created;
    @SerializedName("Date_update")
    public String Date_update;
    @SerializedName("Status_num")
    public String Status_num;
    @SerializedName("Status_text")
    public String Status_text;
    @SerializedName("Customer_name")
    public String Customer_name;
    @SerializedName("Customer_phone")
    public String Customer_phone;
    @SerializedName("Customer_email")
    public String Customer_email;
    @SerializedName("Customer_add")
    public String Customer_add;
}
