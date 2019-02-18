package com.hopper.model.scala.shopping

case class PropertyAvailabilityTotalPrice(var inclusive: PropertyAvailabilityPriceWithCurrency,
                                          var exclusive: PropertyAvailabilityPriceWithCurrency,
                                          var strikeThrough: PropertyAvailabilityPriceWithCurrency,
                                          var marketingFee: PropertyAvailabilityPriceWithCurrency
                                         )
