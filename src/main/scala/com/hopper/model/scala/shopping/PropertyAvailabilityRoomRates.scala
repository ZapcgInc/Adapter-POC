package com.hopper.model.scala.shopping

case class PropertyAvailabilityRoomRates
(
  var nightlyPrice: List[List[PropertyAvailabilityPrice]],private var stayPrice: List[PropertyAvailabilityPrice],private var fees: List[PropertyAvailabilityPrice],private var totals: PropertyAvailabilityTotalPrice
)
