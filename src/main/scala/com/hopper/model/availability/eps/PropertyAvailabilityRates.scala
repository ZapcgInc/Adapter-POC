package com.hopper.model.availability.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
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
    var links: Map[String, String] = _

    @JsonProperty("bed_groups")
    var bedGroups: List[PropertyAvailabilityBedGroups] = _

    @JsonProperty("cancel_penalties")
    var cancelPolicies: List[PropertyAvailabilityCancelPenalties] = _

    @JsonProperty("amenities")
    var amenities: List[PropertyAvailabilityAmenities] = _

    @JsonProperty("occupancies")
    var roomPriceByOccupancy: Map[String, PropertyAvailabilityRoomRates] = _

    @JsonProperty("promo_id")
    var promoId: String = _

    @JsonProperty("promo_description")
    var promoDesc: String = _
}