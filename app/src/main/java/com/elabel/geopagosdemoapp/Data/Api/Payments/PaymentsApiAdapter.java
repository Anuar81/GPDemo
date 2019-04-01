package com.elabel.geopagosdemoapp.Data.Api.Payments;

import com.elabel.geopagosdemoapp.Data.Api.ApiConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentsApiAdapter {

    public PaymentsApiService getPaymentsApiService(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(PaymentsApiService.class);
    }

}
