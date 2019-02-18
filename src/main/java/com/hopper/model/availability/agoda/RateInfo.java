package com.hopper.model.availability.agoda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RateInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class RateInfo {
    @XmlElement(name = "Rate")
    private Rate rate;

    @XmlElement(name = "Included")
    private String included;

    @XmlElement(name = "TotalPaymentAmount")
    private TotalPaymentAmount totalPaymentAmount;

    @XmlElement(name = "Excluded")
    private String excluded;

    @XmlElement(name="PromotionType")
    private PromotionType promotionType;

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(PromotionType promotionType) {
        this.promotionType = promotionType;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public String getIncluded() {
        return included;
    }

    public void setIncluded(String included) {
        this.included = included;
    }

    public TotalPaymentAmount getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(TotalPaymentAmount totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public String getExcluded() {
        return excluded;
    }

    public void setExcluded(String excluded) {
        this.excluded = excluded;
    }

    @Override
    public String toString() {
        return "RateInfo{" +
                "rate=" + rate +
                ", included='" + included + '\'' +
                ", totalPaymentAmount=" + totalPaymentAmount +
                ", excluded='" + excluded + '\'' +
                '}';
    }
}
