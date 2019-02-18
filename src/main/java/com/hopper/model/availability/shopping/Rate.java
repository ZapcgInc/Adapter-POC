package com.hopper.model.availability.shopping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.hopper.model.availability.CancelPolicies;
import com.hopper.model.availability.Link;
import com.hopper.model.availability.RoomPrice;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rate
{
    @JsonProperty("id")
    private String id;

    @JsonProperty("available_rooms")
    private int availableRooms;

    @JsonProperty("refundable")
    private boolean refundable;

    @JsonProperty("deposit_required")
    private boolean depositRequired;

    @JsonProperty("fenced_deal")
    private boolean fencedDeal;

//    @JsonProperty("fenced_deal_available")
//    private boolean fencedDealAvailable;

    @JsonProperty("merchant_of_record")
    private String merchantOfRecord;

    @JsonProperty("links")
    private Map<String, Link> links;

    @JsonProperty("bed_groups")
    private List<BedGroups> bedGroups;

    @JsonProperty("cancel_penalties")
    private List<CancelPolicies> cancelPolicies;

    @JsonProperty("amenities")
    private List<Amenities> amenities;

    @JsonProperty("occupancies")
    private Map<String, RoomPrice> roomPriceByOccupancy;

    @JsonProperty("promo_id")
    private String promoId;

    @JsonProperty("promo_description")
    private String promoDesc;

    public Rate(){}

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

    public String getPromoDesc() {
        return promoDesc;
    }

    public void setPromoDesc(String promoDesc) {
        this.promoDesc = promoDesc;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    public void setRefundable(boolean refundable) {
        this.refundable = refundable;
    }

    public void setDepositRequired(boolean depositRequired) {
        this.depositRequired = depositRequired;
    }

    public void setFencedDeal(boolean fencedDeal) {
        this.fencedDeal = fencedDeal;
    }

//    public void setFencedDealAvailable(boolean fencedDealAvailable) {
//        this.fencedDealAvailable = fencedDealAvailable;
//    }
//
    public void setMerchantOfRecord(String merchantOfRecord) {
        this.merchantOfRecord = merchantOfRecord;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public void setBedGroups(List<BedGroups> bedGroups) {
        this.bedGroups = bedGroups;
    }

    public void setCancelPolicies(List<CancelPolicies> cancelPolicies) {
        this.cancelPolicies = cancelPolicies;
    }

    public void setAmenities(List<Amenities> amenities) {
        this.amenities = amenities;
    }

    public String getId()
    {
        return id;
    }

    public int getAvailableRooms()
    {
        return availableRooms;
    }

    public boolean isRefundable()
    {
        return refundable;
    }

    public boolean isDepositRequired()
    {
        return depositRequired;
    }

    public boolean isFencedDeal()
    {
        return fencedDeal;
    }

//    public boolean isFencedDealAvailable()
//    {
//        return fencedDealAvailable;
//    }

    public String getMerchantOfRecord()
    {
        return merchantOfRecord;
    }

    public Map<String, Link> getLinks()
    {
        return links;
    }

    public List<BedGroups> getBedGroups()
    {
        return bedGroups != null ? bedGroups : ImmutableList.of();
    }

    public List<CancelPolicies> getCancelPolicies()
    {
        return cancelPolicies != null ? cancelPolicies : cancelPolicies;
    }

    public List<Amenities> getAmenities()
    {
        return amenities != null ? amenities : ImmutableList.of();
    }

    public Map<String, RoomPrice> getRoomPriceByOccupancy()
    {
        return roomPriceByOccupancy != null ? roomPriceByOccupancy : ImmutableMap.of();
    }

    public void setRoomPriceByOccupancy(Map<String, RoomPrice> roomPriceByOccupancy) {
        this.roomPriceByOccupancy = roomPriceByOccupancy;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "id='" + id + '\'' +
                ", availableRooms=" + availableRooms +
                ", refundable=" + refundable +
                ", depositRequired=" + depositRequired +
                ", fencedDeal=" + fencedDeal +
//                ", fencedDealAvailable=" + fencedDealAvailable +
                ", merchantOfRecord='" + merchantOfRecord + '\'' +
                ", links=" + links +
                ", bedGroups=" + bedGroups +
                ", cancelPolicies=" + cancelPolicies +
                ", amenities=" + amenities +
                ", roomPriceByOccupany=" + roomPriceByOccupancy +
                '}';
    }
}
