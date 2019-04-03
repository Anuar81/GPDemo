package com.elabel.geopagosdemoapp.Data.Repositories;

import android.util.Log;

import com.elabel.geopagosdemoapp.Data.Api.ApiConstants;
import com.elabel.geopagosdemoapp.Data.Api.Payments.PaymentsApiAdapter;
import com.elabel.geopagosdemoapp.Data.Api.Payments.PaymentsApiService;
import com.elabel.geopagosdemoapp.Model.Bank.Bank;
import com.elabel.geopagosdemoapp.Model.Installments.Installment;
import com.elabel.geopagosdemoapp.Model.Installments.PayerCost;
import com.elabel.geopagosdemoapp.Model.PM.PM;
import com.elabel.geopagosdemoapp.Model.Preference.Preference;
import com.elabel.geopagosdemoapp.VM.PayRepoInt;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentsRepository {
    private PaymentsApiAdapter apiAdapter;
    private PaymentsApiService apiService;
    private MutableLiveData<List<PM>>pmList;
    private MutableLiveData<List<Bank>>bankList;
    private MutableLiveData<List<PayerCost>>pcList;
    //private MutableLiveData<Preference>preferenceID;
    private PayRepoInt payRepoInt;


    public PaymentsRepository(PayRepoInt payRepoInt){
        apiAdapter = new PaymentsApiAdapter();
        apiService = apiAdapter.getPaymentsApiService();
        pmList = new MutableLiveData<>();
        bankList = new MutableLiveData<>();
        pcList = new MutableLiveData<>();
        //preferenceID = new MutableLiveData<>();
        this.payRepoInt = payRepoInt;
    }

    public LiveData<List<PM>>getPMList(){
        apiService.getPaymentsMethods(ApiConstants.API_KEY)
                .enqueue(new Callback<List<PM>>() {
                    @Override
                    public void onResponse(Call<List<PM>> call, Response<List<PM>> response) {
                        if(response.isSuccessful()){
                            pmList.setValue(response.body());
                        }
                        //TODO send msg not succesfull
                    }

                    @Override
                    public void onFailure(Call<List<PM>> call, Throwable t) {
                        //TODO evaluate onFailure
                    }
                });
        return pmList;
    }

    public LiveData<List<Bank>>getBankList(String pmID){
        apiService.getBanks(ApiConstants.API_KEY, pmID)
                .enqueue(new Callback<List<Bank>>() {
                    @Override
                    public void onResponse(Call<List<Bank>> call, Response<List<Bank>> response) {
                        if(response.isSuccessful()){
                            bankList.setValue(response.body());
                        }
                        //TODO send msg not succesfull
                    }

                    @Override
                    public void onFailure(Call<List<Bank>> call, Throwable t) {
                        //TODO evaluate onFailure
                        Log.d("ver",t.toString());
                    }
                });
        return bankList;
    }

    public LiveData<List<PayerCost>>getInstallments(String pmID, String amount, String bankID){
        apiService.getInstallments(ApiConstants.API_KEY, pmID, amount, bankID)
                .enqueue(new Callback<List<Installment>>() {
                    @Override
                    public void onResponse(Call<List<Installment>> call, Response<List<Installment>> response) {
                        if(response.isSuccessful()){
                            pcList.setValue(response.body().get(0).getPayerCosts());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Installment>> call, Throwable t) {
                    }
                });
        return pcList;
    }

    public void getPreferenceID(String preferenceBodyJson){
        apiService.getPreferencesID(ApiConstants.API_KEY_AT, RequestBody.create(MediaType.parse("application/json"), preferenceBodyJson))
                .enqueue(new Callback<Preference>() {
                    @Override
                    public void onResponse(Call<Preference> call, Response<Preference> response) {
                        if(response.isSuccessful()){
                            payRepoInt.informPreferenceID(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Preference> call, Throwable t) {
                    }
                });
    }
}
