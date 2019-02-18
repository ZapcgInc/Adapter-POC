package com.hopper.model.scala.shopping

case class PropertyAvailabilityCancelPenalties
(
  /* Currency of the amount object. */
  currency: String,
  /* Effective date and time of cancellation penalty in extended ISO 8601 format, with ±hh:mm timezone offset */
  start: Option[String],
  /* End date and time of cancellation penalty in extended ISO 8601 format, with ±hh:mm timezone offset */
  end: Option[String],
  /* Penalty amount in request currency. */
  amount: Option[String],
  /* Number of nights charged for as penalty. */
  nights: Option[String],
  /* Percentage of total booking charged for as penalty. A thirty percent penalty would be returned as 0.3 */
  percent: Option[String]
)
