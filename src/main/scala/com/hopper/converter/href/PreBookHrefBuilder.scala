package com.hopper.converter.href

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.hopper.commons.util.GZIPStringURLEncoder
import com.hopper.model.agoda.availability.request.AvailabilityRequestV2
import com.hopper.model.agoda.availability.response.{Hotel, RateInfo, Room}

object PreBookHrefBuilder
{
    private val PRICE_CHECK_HREF_TEMPLATE: String = s"/properties/availability/%s/rooms/%s/rates/%s/price-check?token=%s"

    def buildHref(room: Room, hotel: Hotel, request: AvailabilityRequestV2): String =
    {
        val tokenInfo: PriceCheckToken = new PriceCheckToken(new AvailabilityRequestParams(request), room.rateInfo)
        val jsonResponse: String = (new ObjectMapper).registerModule(DefaultScalaModule).writeValueAsString(tokenInfo)
        val encodedToken: String = GZIPStringURLEncoder.encodeString(jsonResponse)

        PRICE_CHECK_HREF_TEMPLATE.format(hotel.id, room.id, room.rateCategoryID, encodedToken)
    }

    def getShopRequestDetail(token: String): PriceCheckToken =
    {
        val jsonString: String = GZIPStringURLEncoder.decodeString(token)

        val objectMapper = new ObjectMapper() with ScalaObjectMapper
        objectMapper.registerModule(DefaultScalaModule)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        objectMapper.readValue[PriceCheckToken](jsonString)
    }

    case class PriceCheckToken(requestParams: AvailabilityRequestParams, rateInfo: RateInfo)
    {
        /*No-arg constructor for JSON Object Mapper*/
        def this()
        {
            this(null, null)
        }
    }

    class AvailabilityRequestParams
    {
        var checkInDate: String = _
        var checkOutDate: String = _

        var roomCount: Int = _
        var adultsCount: Int = _
        var childrenCount: Int = _
        var childrenAges: Array[Int] = _

        var language: String = _
        var currency: String = _
        var occupancy: List[String] = _

        def this(request: AvailabilityRequestV2)
        {
            this()
            checkInDate = request.checkInDate
            checkOutDate = request.checkOutDate
            roomCount = request.roomCount
            adultsCount = request.adultsCount
            childrenCount = request.childrenCount
            childrenAges = request.childrenAges
            language = request.language
            currency = request.currency
            occupancy = request.occupancy
        }
    }

}
