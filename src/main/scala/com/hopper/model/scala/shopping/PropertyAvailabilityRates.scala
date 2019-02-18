package com.hopper.model.scala.shopping

import java.math.BigDecimal

case class PropertyAvailabilityRates
(
  /* Unique Identifier for a rate. */
  id: String,
  /* The number of bookable rooms remaining with this rate. Use this value to create rules for urgency messaging to alert users to low availability on busy travel dates or at popular properties. If the value returns as 2147483647 (max int value), the actual value could not be determined. Ensure your urgency messaging ignores such instances when returned. */
  available_rooms: BigDecimal,
  /* Indicates if the rate is fully refundable at the time of booking. Cancel penalties may still apply. Please refer to the cancel penalties section for reference. */
  refundable: Boolean,
  /* Indicates that a \"Member Only Deal\" discount has been applied to the rate. Partners must be enabled to receive \"Member Only Deals\" to benefit from this parameter. If interested, partners should speak to their account representatives. This parameter can be used to merchandise if a \"Member Only Deal\" has been applied which will help partners to drive loyalty. In addition, it can be used by OTA's to create an opaque but public shopping experience. */
  fenced_deal: Boolean,
  /* Indicates if a \"Member Only Deal\" is available for this rate. */
  fenced_deal_available: Boolean,
  /* If a deposit is required for the rate, this value will be `true`. A \"deposit_policies\" link will be included in this response to retrieve more information about the deposit, including amounts and timing. */
  deposit_required: Boolean,
  /* * `expedia` - Payment is taken by Expedia. The payment options avaliable can be discovered using the \"payment-options\" link in the response. This includes AFFILIATE_COLLECT payments. * `property` - Payment is taken by the property.  */
  merchant_of_record: String,
  /* Room amenities. */
  amenities: Option[List[PropertyAvailabilityAmenities]],

  links: Map[String,PropertyAvailabilityLinks],

  bed_groups: Option[List[PropertyAvailabilityBedGroups]],
  /* Array of `cancel_penalty` objects containing cancel penalty information. Will not be populated on non refundable. */
  cancel_penalties: Option[List[PropertyAvailabilityCancelPenalties]],
  occupancies: Map[String, PropertyAvailabilityRoomRates],
  /* Unique identifier for the promotional rate. */
  promo_id: Option[String],
  /* The description for the promotional rate. */
  promo_description: Option[String]
)
