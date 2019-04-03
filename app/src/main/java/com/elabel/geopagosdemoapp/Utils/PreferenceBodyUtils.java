package com.elabel.geopagosdemoapp.Utils;
import com.elabel.geopagosdemoapp.Model.Body.ItemBody;
import com.elabel.geopagosdemoapp.Model.Body.PayerBody;
import com.elabel.geopagosdemoapp.Model.Body.PaymentMethodsBody;
import com.elabel.geopagosdemoapp.Model.Body.PreferenceBody;

import java.util.ArrayList;
import java.util.List;

public class PreferenceBodyUtils {
    private Double amount;
    private Long installment;
    private String pmID;

    public PreferenceBodyUtils(Double amount, Long installment, String pmID) {
        this.amount = amount;
        this.installment = installment;
        this.pmID = pmID;
    }

    public PreferenceBody getCorrectPreferenceBody(){
        PreferenceBody preferenceBody = new PreferenceBody();
        preferenceBody.setItems(getCorrectItemBody());
        preferenceBody.setPayer(getCorrectPayerBody());
        preferenceBody.setPaymentMethods(getCorretPM());
        return preferenceBody;
    }

    private List<ItemBody> getCorrectItemBody(){
        ItemBody itemBody = new ItemBody();
        itemBody.setTitle("GeoPagos Demo Item");
        itemBody.setDescription("Algo");
        itemBody.setQuantity(1L);
        itemBody.setCurrencyId("ARS");
        itemBody.setUnitPrice(amount);
        List<ItemBody> itemBodies = new ArrayList<>();
        itemBodies.add(itemBody);
        return itemBodies;
    }

    private PayerBody getCorrectPayerBody(){
        PayerBody payerBody = new PayerBody();
        payerBody.setEmail("payer@email.com");
        return payerBody;
    }

    private PaymentMethodsBody getCorretPM(){
        PaymentMethodsBody pmb = new PaymentMethodsBody();
        pmb.setInstallments(installment);
        pmb.setDefaultPaymentMethodId(pmID);
        return pmb;
    }
}
