package com.elabel.geopagosdemoapp.Model.Body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayerBody {

    @SerializedName("email")
    @Expose
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
