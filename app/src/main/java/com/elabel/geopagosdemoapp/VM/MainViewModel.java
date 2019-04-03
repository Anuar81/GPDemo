package com.elabel.geopagosdemoapp.VM;

import java.text.DecimalFormat;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel  {
    private Double amount = 0.00;
    private DecimalFormat df = new DecimalFormat(".##");

    public String getAmount() {
        if(amountEnable()){
            return df.format(amount);
        }else{
            return "00.0";
        }

    }

    public void addAmount(String amount){
        sumTotalValue(correctStringforDouble(amount));
    }

    public Boolean amountEnable(){
        if(amount > 0.00){
            return true;
        }else{
            return false;
        }
    }

    private String correctStringforDouble(String amount){
        if (amount.contains(",")){
            return amount.replace(",",".");
        }else {
            return amount;
        }
    }

    private void sumTotalValue(String correctAmount){
         amount = amount + Double.parseDouble(correctAmount);
    }
}
