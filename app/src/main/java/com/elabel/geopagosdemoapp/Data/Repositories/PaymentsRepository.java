package com.elabel.geopagosdemoapp.Data.Repositories;

import android.util.Log;

import com.elabel.geopagosdemoapp.Data.Api.ApiConstants;
import com.elabel.geopagosdemoapp.Data.Api.Payments.PaymentsApiAdapter;
import com.elabel.geopagosdemoapp.Data.Api.Payments.PaymentsApiService;
import com.elabel.geopagosdemoapp.Model.Bank.Bank;
import com.elabel.geopagosdemoapp.Model.PM.PM;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentsRepository {
    private PaymentsApiAdapter apiAdapter;
    private PaymentsApiService apiService;
    private MutableLiveData<List<PM>>pmList;
    private MutableLiveData<List<Bank>>bankList;

    public PaymentsRepository(){
        apiAdapter = new PaymentsApiAdapter();
        apiService = apiAdapter.getPaymentsApiService();
        pmList = new MutableLiveData<>();
        bankList = new MutableLiveData<>();
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
}
