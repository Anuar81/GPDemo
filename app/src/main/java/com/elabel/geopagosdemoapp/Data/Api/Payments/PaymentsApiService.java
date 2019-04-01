package com.elabel.geopagosdemoapp.Data.Api.Payments;

import com.elabel.geopagosdemoapp.Data.Api.ApiConstants;
import com.elabel.geopagosdemoapp.Model.Bank.Bank;
import com.elabel.geopagosdemoapp.Model.PM.PM;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PaymentsApiService {

    //PM
    @GET(ApiConstants.PM)
    Call<List<PM>> getPaymentsMethods(@Query(ApiConstants.PUBLIC_KEY_QUERY) String PublicKey);

    //Bank
    @GET(ApiConstants.BANKS)
    Call<List<Bank>> getBanks(@Query(ApiConstants.PUBLIC_KEY_QUERY) String PublicKey,
                              @Query(ApiConstants.BANKS_CARDID_QUERY) String pmKey);
}
