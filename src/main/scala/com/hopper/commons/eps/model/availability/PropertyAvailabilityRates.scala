package com.hopper.commons.eps.model.availability

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class PropertyAvailabilityRates
{
    @JsonProperty("id")
    var id: String = _

    @JsonProperty("available_rooms")
    var availableRooms: Int = 0

    @JsonProperty("refundable")
    var refundable: Boolean = false

    @JsonProperty("deposit_required")
    var depositRequired: Boolean = false

    @JsonProperty("fenced_deal")
    var fencedDeal: Boolean = false

    @JsonProperty("merchant_of_record")
    var merchantOfRecordString: String = _

    @JsonProperty("links")
    var links: Map[String, PropertyAvailabilityLinks] = _

    @JsonProperty("bed_groups")
    var bedGroups: Array[PropertyAvailabilityBedGroups] = _

    @JsonProperty("cancel_penalties")
    var cancelPolicies: Array[PropertyAvailabilityCancelPenalties] = _

    @JsonProperty("amenities")
    var amenities: Array[PropertyAvailabilityAmenities] = _

    @JsonProperty("occupancies")
    var roomPriceByOccupancy: Map[String, PropertyAvailabilityRoomRates] = _

    @JsonProperty("promo_id")
    var promoId: String = _

    @JsonProperty("promo_description")
    var promoDesc: String = _

    override def toString = s"PropertyAvailabilityRates($id, $availableRooms, $refundable, $depositRequired, $fencedDeal, $merchantOfRecordString, $links, $bedGroups, $cancelPolicies, $amenities, $roomPriceByOccupancy, $promoId, $promoDesc)"
}