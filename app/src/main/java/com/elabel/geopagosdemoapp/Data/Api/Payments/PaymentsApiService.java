package com.elabel.geopagosdemoapp.Data.Api.Payments;

import com.elabel.geopagosdemoapp.Data.Api.ApiConstants;
import com.elabel.geopagosdemoapp.Model.Bank.Bank;
import com.elabel.geopagosdemoapp.Model.Installments.Installment;
import com.elabel.geopagosdemoapp.Model.PM.PM;
import com.elabel.geopagosdemoapp.Model.Preference.Preference;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PaymentsApiService {

    //PM
    @GET(ApiConstants.PM)
    Call<List<PM>> getPaymentsMethods(@Query(ApiConstants.PUBLIC_KEY_QUERY) String publicKey);

    //Bank
    @GET(ApiConstants.BANKS)
    Call<List<Bank>> getBanks(@Query(ApiConstants.PUBLIC_KEY_QUERY) String publicKey,
                              @Query(ApiConstants.BANKS_CARDID_QUERY) String pmKey);

    //Installments
    @GET(ApiConstants.INSTALLMENTS)
    Call<List<Installment>> getInstallments(@Query(ApiConstants.PUBLIC_KEY_QUERY) String publicKey,
                                            @Query(ApiConstants.INSTALLMENTS_CARDID_QUERY) String pmKey,
                                            @Query(ApiConstants.INSTALLMENTS_AMOUNT_QUERY) String amount,
                                            @Query(ApiConstants.INSTALLMENTS_ISSUER_QUERY) String bankID);

    //PreferenceID
    @POST(ApiConstants.PREFERENCESID)
    Call<Preference> getPreferencesID(@Query(ApiConstants.PREFERENCESID_AT) String publicKey,
                                      @Body RequestBody json);
}
