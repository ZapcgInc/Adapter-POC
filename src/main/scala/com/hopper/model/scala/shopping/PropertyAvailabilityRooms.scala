package com.hopper.model.scala.shopping

case class PropertyAvailabilityRooms(
  /* Unique Identifier for a room type. */
  id: String,
  /* Name of the room type. */
  room_name: String,
  /* Array of objects containing rate information. */
  rates: List[PropertyAvailabilityRates])
