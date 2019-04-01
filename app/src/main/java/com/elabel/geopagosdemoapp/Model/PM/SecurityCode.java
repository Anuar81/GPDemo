package com.elabel.geopagosdemoapp.Model.PM;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SecurityCode {

    @SerializedName("length")
    @Expose
    private Long length;
    @SerializedName("card_location")
    @Expose
    private String cardLocation;
    @SerializedName("mode")
    @Expose
    private String mode;

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public String getCardLocation() {
        return cardLocation;
    }

    public void setCardLocation(String cardLocation) {
        this.cardLocation = cardLocation;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

}
