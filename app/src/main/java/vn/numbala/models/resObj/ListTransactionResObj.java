package vn.numbala.models.resObj;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.numbala.models.TransactionModel;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 12/2/17 12:54 PM.
 * Project Name: Numbala
 */

public class ListTransactionResObj extends SVResObj {

    @SerializedName("Data")
    public List<TransactionModel> data;
}
