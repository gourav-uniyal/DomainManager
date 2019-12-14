package com.android.testmessenger.interfaces;

import com.android.testmessenger.model.DomainFilter;
import com.android.testmessenger.model.ResponseDomain;
import com.android.testmessenger.model.ResponseMessage;
import com.android.testmessenger.model.ResponseVerification;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("filter")
    Call<DomainFilter> getFilters();

    @POST("domains")
    Call<ResponseDomain> getDomainList(@Query( "page" ) int page, @Body HashMap hashMap);

    @POST("keys")
    Call<ResponseVerification> verificationTask(@Body HashMap verifier);

    @POST("message")
    Call<ResponseMessage> getMessage(@Body HashMap hashMap);
}