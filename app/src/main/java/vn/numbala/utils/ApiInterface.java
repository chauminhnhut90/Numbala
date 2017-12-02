package vn.numbala.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.numbala.models.resObj.LoginResObj;

public interface ApiInterface {

    @GET("{key}/{typ}/{imei}/{dev}/{takeRows}")
    Call<LoginResObj> login(@Path("key") String key,
                                @Path("typ") int typ,
                                @Path("imei") String imei,
                                @Path("dev") String dev,
                                @Path("ip") String ip);


}
