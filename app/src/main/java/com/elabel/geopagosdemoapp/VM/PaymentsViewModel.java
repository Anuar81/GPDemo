package com.elabel.geopagosdemoapp.VM;

import com.elabel.geopagosdemoapp.Data.Repositories.PaymentsRepository;
import com.elabel.geopagosdemoapp.Model.Bank.Bank;
import com.elabel.geopagosdemoapp.Model.Installments.PayerCost;
import com.elabel.geopagosdemoapp.Model.PM.PM;
import com.elabel.geopagosdemoapp.Model.Body.PreferenceBody;
import com.elabel.geopagosdemoapp.Model.Preference.Preference;
import com.elabel.geopagosdemoapp.Utils.PreferenceBodyUtils;
import com.elabel.geopagosdemoapp.Utils.StepsNumbersUtils;
import com.google.gson.Gson;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PaymentsViewModel extends ViewModel implements PayRepoInt {
    private Double amount = 0.00;
    private Integer step = 0;
    private String pmID = "";
    private String pmUrlImg = "";
    private String pmLegend = "";
    private String bankID = "";
    private String bankUrlImg = "";
    private String bankLegend = "";
    private String installmentsTotal = "";
    private String installmentMessage = "";
    private LiveData<List<PM>>pmListLD;
    private LiveData<List<Bank>>bankListLD;
    private LiveData<List<PayerCost>> pcListLD;
    private MutableLiveData<Preference> preferenceIDLD;
    private PaymentsRepository repository;

    public PaymentsViewModel() {
        this.repository = new PaymentsRepository(this);
    }

    //getters and setters
    public Integer getStep() {
        return step;
    }

    public Double getAmount() {
        return amount;
    }

    public String getPmLegend() {
        return pmLegend;
    }

    public String getBankLegend() {
        return bankLegend;
    }

    public String getInstallmentMessage() {
        return installmentMessage;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public void setInstallmentsTotal(String installmentsTotal) {
        this.installmentsTotal = installmentsTotal;
    }

    public void setInstallmentMessage(String installmentMessage) {
        this.installmentMessage = installmentMessage;
    }

    //

    public void saveDatainVM(String legend, String id, String imgOrTxt){
        switch (step){
            case StepsNumbersUtils.INITIAL_STEP:
                pmID = id;
                pmUrlImg = imgOrTxt;
                pmLegend = legend;
                break;
            case StepsNumbersUtils.BANK_STEP:
                bankID = id;
                bankUrlImg = imgOrTxt;
                bankLegend = legend;
                break;
            case StepsNumbersUtils.INSTALLSMENTS_STEP:
                installmentsTotal = id;
                installmentMessage = imgOrTxt;
        }
    }

    public boolean goBack(){
        step--;
        if(step < 1){
            return false;
        }else{
            return true;
        }

    }

    public boolean goFordward(){
        step++;
        if (checkDataAndCont()){
            return true;
        }else{
            return false;
        }
    }



    public String getCardTitle(){
        switch (step){
            case StepsNumbersUtils.INITIAL_STEP:
                return "Seleccione un medio de pago";
            case StepsNumbersUtils.BANK_STEP:
                return "Seleccione su banco";
            case StepsNumbersUtils.INSTALLSMENTS_STEP:
                return "Seleccione cantidad de cuotas";
            case StepsNumbersUtils.FINAL_STEP:
                return "Realizar Pago";
            default:
                return "";
        }
    }

    public String getUrlOrTxt(){
        switch (step){
            case StepsNumbersUtils.INITIAL_STEP:
                return pmUrlImg;
            case StepsNumbersUtils.BANK_STEP:
                return bankUrlImg;
            case StepsNumbersUtils.INSTALLSMENTS_STEP:
                return installmentMessage;
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

    public void getInstallments(){
        pcListLD = repository.getInstallments(pmID,amount.toString(),bankID);
    }

    public void getPreference(){
        PreferenceBodyUtils preferenceBodyUtils = new PreferenceBodyUtils(amount,
                Long.valueOf(installmentsTotal),
                pmID);
        repository.getPreferenceID(getJsonFromPreference(preferenceBodyUtils.getCorrectPreferenceBody()));
        //preferenceIDLD = repository.getPreferenceID(getJsonFromPreference(preferenceBodyUtils.getCorrectPreferenceBody()));
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

    public LiveData<List<PayerCost>> getPcListLD(){
        if(pcListLD == null){
            pcListLD = new MutableLiveData<>();
        }
        return pcListLD;
    }

    public LiveData<Preference>getPreferenceIDLD(){
        if(preferenceIDLD == null){
            preferenceIDLD = new MutableLiveData<>();
        }
        return preferenceIDLD;
    }

    private boolean checkDataAndCont(){
        if(step.equals(StepsNumbersUtils.BANK_STEP) && !pmID.isEmpty()){
            getBanks();
            return true;
        }else if(step.equals(StepsNumbersUtils.INSTALLSMENTS_STEP) && !bankID.isEmpty()){
            getInstallments();
            return true;
        }else if(step.equals(StepsNumbersUtils.FINAL_STEP) && !installmentsTotal.isEmpty() && !installmentMessage.isEmpty()){
            return true;
        }else{
            step--;
            return false;
        }
    }

    private String getJsonFromPreference(PreferenceBody preferenceBody){
        Gson gson = new Gson();
        return gson.toJson(preferenceBody);
    }


    @Override
    public void informPreferenceID(Preference preference) {
        preferenceIDLD.setValue(preference);
    }
}
