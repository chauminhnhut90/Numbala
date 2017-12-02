package vn.numbala.models.resObj;

import com.google.gson.annotations.SerializedName;

import vn.numbala.models.LoginModel;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 11/29/17 10:30 PM.
 * Project Name: Numbala
 */

public class LoginResObj extends SVResObj {

    @SerializedName("Data")
    public LoginModel data;
}
