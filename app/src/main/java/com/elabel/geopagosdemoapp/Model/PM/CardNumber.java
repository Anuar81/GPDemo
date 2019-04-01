package com.elabel.geopagosdemoapp.Model.PM;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardNumber {

    @SerializedName("validation")
    @Expose
    private String validation;
    @SerializedName("length")
    @Expose
    private Long length;

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

}
