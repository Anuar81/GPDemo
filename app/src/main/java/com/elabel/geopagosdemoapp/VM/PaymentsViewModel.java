package com.elabel.geopagosdemoapp.VM;

import com.elabel.geopagosdemoapp.Data.Repositories.PaymentsRepository;
import com.elabel.geopagosdemoapp.Model.Bank.Bank;
import com.elabel.geopagosdemoapp.Model.PM.PM;
import com.elabel.geopagosdemoapp.Utils.StepsNumbersUtils;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PaymentsViewModel extends ViewModel {
    private Double amount = 0.00;
    private Integer step = 0;
    private String pmID = "";
    private String bankID = "";
    private LiveData<List<PM>>pmListLD;
    private LiveData<List<Bank>>bankListLD;
    private PaymentsRepository repository;

    public PaymentsViewModel() {
        this.repository = new PaymentsRepository();
    }

    //getters and setters
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public void setPmID(String pmID) {
        this.pmID = pmID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    //

    public boolean goFordward(){
        step++;
        if (checkDataToCont()){
            makeNextInetCall();
            return true;
        }else{
            return false;
        }
    }

    private boolean checkDataToCont(){
        if(step.equals(StepsNumbersUtils.BANK_STEP) && !pmID.isEmpty()){
            return true;
        }else if(step.equals(StepsNumbersUtils.INSTALLSMENTS_STEP) && !bankID.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    private void makeNextInetCall(){
        if(step.equals(StepsNumbersUtils.BANK_STEP)){
            getBanks();
        }
    }

    public String getCardTitle(){
        switch (step){
            case 1:
                return "Seleccione un medio de pago";
            case 2:
                return "Seleccione su banco";
            case 3:
                return "Seleccione cantidad de cuotas";
            default:
                return "";
        }
    }

    public void getPaymentsMethods(){
        pmListLD = repository.getPMList();
    }

    public void getBanks(){
        bankListLD = repository.getBankList(pmID);
    }

    public LiveData<List<PM>> getPmListLD() {
        if(pmListLD == null){
            getPaymentsMethods();
        }
        return pmListLD;
    }

    public LiveData<List<Bank>> getBanksListLD() {
        if(bankListLD == null){
            bankListLD = new MutableLiveData<>();
        }
        return bankListLD;
    }


}
