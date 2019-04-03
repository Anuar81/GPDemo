package com.elabel.geopagosdemoapp.Model.Installments;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayerCost {

    @SerializedName("installments")
    @Expose
    private Long installments;
    @SerializedName("installment_rate")
    @Expose
    private Double installmentRate;
    @SerializedName("discount_rate")
    @Expose
    private Long discountRate;
    @SerializedName("labels")
    @Expose
    private List<String> labels = null;
    @SerializedName("installment_rate_collector")
    @Expose
    private List<String> installmentRateCollector = null;
    @SerializedName("min_allowed_amount")
    @Expose
    private Long minAllowedAmount;
    @SerializedName("max_allowed_amount")
    @Expose
    private Long maxAllowedAmount;
    @SerializedName("recommended_message")
    @Expose
    private String recommendedMessage;
    @SerializedName("installment_amount")
    @Expose
    private Double installmentAmount;
    @SerializedName("total_amount")
    @Expose
    private Double totalAmount;

    public Long getInstallments() {
        return installments;
    }

    public void setInstallments(Long installments) {
        this.installments = installments;
    }

    public Double getInstallmentRate() {
        return installmentRate;
    }

    public void setInstallmentRate(Double installmentRate) {
        this.installmentRate = installmentRate;
    }

    public Long getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Long discountRate) {
        this.discountRate = discountRate;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getInstallmentRateCollector() {
        return installmentRateCollector;
    }

    public void setInstallmentRateCollector(List<String> installmentRateCollector) {
        this.installmentRateCollector = installmentRateCollector;
    }

    public Long getMinAllowedAmount() {
        return minAllowedAmount;
    }

    public void setMinAllowedAmount(Long minAllowedAmount) {
        this.minAllowedAmount = minAllowedAmount;
    }

    public Long getMaxAllowedAmount() {
        return maxAllowedAmount;
    }

    public void setMaxAllowedAmount(Long maxAllowedAmount) {
        this.maxAllowedAmount = maxAllowedAmount;
    }

    public String getRecommendedMessage() {
        return recommendedMessage;
    }

    public void setRecommendedMessage(String recommendedMessage) {
        this.recommendedMessage = recommendedMessage;
    }

    public Double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(Double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

}
