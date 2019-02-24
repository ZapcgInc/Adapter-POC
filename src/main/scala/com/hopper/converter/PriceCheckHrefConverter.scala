package com.hopper.converter

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.nio.charset.StandardCharsets
import java.util.Base64
import java.util.zip.{GZIPInputStream, GZIPOutputStream}

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.hopper.model.availability.agoda.request.AvailabilityRequestV2
import com.hopper.model.availability.agoda.response.{Hotel, RateInfo, Room}
import com.twitter.io.StreamIO

object PriceCheckHrefConverter
{
    private val PRICE_CHECK_HREF_TEMPLATE: String = s"/properties/availability/%s/rooms/%s/rates/%s/price-check?token=%s"

    def buildHref(room: Room, hotel: Hotel, request: AvailabilityRequestV2): String =
    {
        val tokenInfo: PriceCheckToken = new PriceCheckToken(new AvailabilityRequestParams(request), room.rateInfo)
        val jsonResponse: String = (new ObjectMapper).registerModule(DefaultScalaModule).writeValueAsString(tokenInfo)
        val encodedToken: String = _encode(jsonResponse)

        println("Encoded" + encodedToken)
        println("Decoded" + _decode(encodedToken))
        PRICE_CHECK_HREF_TEMPLATE.format(hotel.id, room.id, room.rateCategoryID, encodedToken)
    }

    def getShopRequestDetail(token: String): PriceCheckToken =
    {
        println("Decoding : " + token)
        val jsonString: String = _decode(token)

        val objectMapper = new ObjectMapper() with ScalaObjectMapper
        objectMapper.registerModule(DefaultScalaModule)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        objectMapper.readValue[PriceCheckToken](jsonString)
    }

    def _encode(input: String): String =
    {

        val byteArrayOutputStream: ByteArrayOutputStream = new ByteArrayOutputStream
        val gzipOutputStream: GZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream)
        gzipOutputStream.write(input.getBytes(StandardCharsets.UTF_8))

        gzipOutputStream.finish()

        byteArrayOutputStream.toByteArray

        Base64.getUrlEncoder
          .withoutPadding()
          .encodeToString(byteArrayOutputStream.toByteArray)
    }

    def _decode(input: String): String =
    {
        val decoded: Array[Byte] = Base64.getUrlDecoder.decode(input)
        val baos = new ByteArrayOutputStream

        StreamIO.copy(new GZIPInputStream(new ByteArrayInputStream(decoded)), baos)

        new String(baos.toByteArray, "UTF-8")
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
            language = request.language
            currency = request.currency
            occupancy = request.occupancy
        }
    }

}
