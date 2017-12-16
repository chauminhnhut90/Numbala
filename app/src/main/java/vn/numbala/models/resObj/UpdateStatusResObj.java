package vn.numbala.models.resObj;

import com.google.gson.annotations.SerializedName;

import vn.numbala.models.UpdateStatusModel;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 12/16/17 10:41 AM.
 * Project Name: Numbala
 */

public class UpdateStatusResObj extends SVResObj {

    @SerializedName("Data")
    public UpdateStatusModel data;
}
