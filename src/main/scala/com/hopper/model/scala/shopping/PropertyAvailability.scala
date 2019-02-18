import com.fasterxml.jackson.annotation.JsonProperty
import com.hopper.model.scala.shopping.{PropertyAvailabilityLinks, PropertyAvailabilityRooms}

import scala.beans.BeanProperty

case class PropertyAvailability
(
  var property_id: String,
  /* Array of objects containing room information. */
  var rooms: List[PropertyAvailabilityRooms],
  var links: Map[String,PropertyAvailabilityLinks])

