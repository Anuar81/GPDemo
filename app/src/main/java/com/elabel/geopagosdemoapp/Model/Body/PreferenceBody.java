package com.elabel.geopagosdemoapp.Model.Body;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreferenceBody {

    @SerializedName("items")
    @Expose
    private List<ItemBody> items = null;
    @SerializedName("payment_methods")
    @Expose
    private PaymentMethodsBody paymentMethods;
    @SerializedName("payer")
    @Expose
    private PayerBody payer;

    public List<ItemBody> getItems() {
        return items;
    }

    public void setItems(List<ItemBody> items) {
        this.items = items;
    }

    public PaymentMethodsBody getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(PaymentMethodsBody paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public PayerBody getPayer() {
        return payer;
    }

    public void setPayer(PayerBody payer) {
        this.payer = payer;
    }

}
