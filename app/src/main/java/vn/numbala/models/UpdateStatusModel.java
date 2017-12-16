package vn.numbala.models;

import com.google.gson.annotations.SerializedName;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 12/16/17 10:36 AM.
 * Project Name: Numbala
 */

public class UpdateStatusModel {

    @SerializedName("Status_num")
    public int statusNum;
    @SerializedName("Status_text")
    public String statusText;
    @SerializedName("User_Info")
    public UserInfo userInfo;

    public static class UserInfo {
        @SerializedName("Customer_name")
        public String customerName;
        @SerializedName("Customer_phone")
        public String customerPhone;
        @SerializedName("Customer_message")
        public String customerMessage;
    }
}
