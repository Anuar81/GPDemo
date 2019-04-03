package com.elabel.geopagosdemoapp.Model.Body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMethodsBody {

    @SerializedName("installments")
    @Expose
    private Long installments;
    @SerializedName("default_payment_method_id")
    @Expose
    private String defaultPaymentMethodId;

    public Long getInstallments() {
        return installments;
    }

    public void setInstallments(Long installments) {
        this.installments = installments;
    }

    public String getDefaultPaymentMethodId() {
        return defaultPaymentMethodId;
    }

    public void setDefaultPaymentMethodId(String defaultPaymentMethodId) {
        this.defaultPaymentMethodId = defaultPaymentMethodId;
    }

}
