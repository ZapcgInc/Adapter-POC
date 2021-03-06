package com.hopper.model.agoda.availability.request

import java.io.StringWriter

import com.hopper.converter.href.PreBookHrefBuilder.PriceCheckToken
import com.hopper.model.agoda.availability.request.constants.AgodaAvailabilityRequestType
import com.hopper.model.constants.AvailabilityRequestParams
import com.twitter.finagle.http.Request
import javax.xml.bind.annotation.{XmlElement, _}
import javax.xml.bind.{JAXBContext, JAXBException, Marshaller}
import org.apache.commons.lang.StringUtils

import scala.collection.JavaConversions._

@XmlRootElement(name = "AvailabilityRequestV2")
@XmlAccessorType(XmlAccessType.FIELD)
class AvailabilityRequestV2
{
    @XmlTransient
    private val AGODA_REQUEST_MARSHALLER: Marshaller = JAXBContext.newInstance(classOf[AvailabilityRequestV2]).createMarshaller

    @XmlAttribute(name = "siteid")
    val siteId: String = "1812488"

    @XmlAttribute(name = "apikey")
    val apiKey: String = "6fae573e-b261-4c02-97b4-3dd20d1e74b2"

    @XmlElement(name = "Type")
    var requestType: Int = _

    @XmlElement(name = "Id")
    var propertyIds: String = _

    @XmlElement(name = "CheckIn")
    var checkInDate: String = _

    @XmlElement(name = "CheckOut")
    var checkOutDate: String = _

    @XmlElement(name = "Rooms")
    var roomCount: Int = _

    @XmlElement(name = "Adults")
    var adultsCount: Int = _

    @XmlElement(name = "Children")
    var childrenCount: Int = _

    @XmlElementWrapper(name = "ChildrenAges")
    @XmlElement(name = "Age")
    var childrenAges: Array[Int] = _

    @XmlElement(name = "Language")
    var language: String = _

    @XmlElement(name = "Currency")
    var currency: String = _

    @XmlTransient
    var occupancy: List[String] = _

    def this(hotelID: String, token: PriceCheckToken)
    {
        this()
        requestType = AgodaAvailabilityRequestType.get(1)
        propertyIds = hotelID

        checkInDate = token.requestParams.checkInDate
        checkOutDate = token.requestParams.checkOutDate

        roomCount = token.requestParams.roomCount
        adultsCount = token.requestParams.adultsCount
        childrenCount = token.requestParams.childrenCount
        childrenAges = token.requestParams.childrenAges

        occupancy = token.requestParams.occupancy
        currency = token.requestParams.currency
        language = token.requestParams.language
    }

    def this(request: Request)
    {
        this()
        _populateRequestInfo(request)
        _populatePropertyID(request)
    }

    def _populateRequestInfo(request: Request): Unit =
    {
        var occupancies = List[String]()
        // TODO : I think this should be headers.
        for (entry <- request.getParams().iterator())
        {
            val k = entry.getKey
            val v = entry.getValue
            AvailabilityRequestParams.withNameOpt(k) match
            {
                case Some(AvailabilityRequestParams.CHECKIN_PARAM_KEY) =>
                {
                    checkInDate = v
                }

                case Some(AvailabilityRequestParams.CHECKOUT_PARAM_KEY) =>
                {
                    checkOutDate = v
                }
                case Some(AvailabilityRequestParams.CURRENCY_CODE_KEY) =>
                {
                    currency = v
                }

                case Some(AvailabilityRequestParams.LANGUAGE_CODE_KEY) =>
                {
                    language = v.toLowerCase()
                }

                case Some(AvailabilityRequestParams.OCCUPANCY_KEY) =>
                {
                    occupancies = v :: occupancies
                    _populateOccupancy(v)
                }

                case _ =>
                {

                }
            }
        }
        occupancy = occupancies
        roomCount = occupancy.size
    }

    def _populatePropertyID(request: Request): Unit =
    {
        import com.hopper.model.agoda.availability.request.constants.AgodaAvailabilityRequestType
        val requestPropertyIds: List[String] = request.getParams(AvailabilityRequestParams.PROPERTY_ID.toString)
          .filter(p => StringUtils.isNotBlank(p))
          .toList

        propertyIds = requestPropertyIds.mkString(",")
        requestType = AgodaAvailabilityRequestType.get(propertyIds.size)
    }

    def _populateOccupancy(reqOccupancy: String): Unit =
    {
        val split: Array[String] = reqOccupancy.split("-")
        adultsCount = split(0).toInt
        if (split.length == 2)
        {
            childrenAges = split(1).split(",").map(_.toInt)
            childrenCount = childrenAges.length
        }
    }

    def convertToXML(): String =
    {
        try
        {
            val stringWriter: StringWriter = new StringWriter
            AGODA_REQUEST_MARSHALLER.marshal(this, stringWriter)
            stringWriter.toString
        }
        catch
        {
            case jexp: JAXBException =>
            {
                throw new RuntimeException("Failed to marshall availability request")
            }
        }
    }
}

object AvailabilityRequestV2 extends AvailabilityRequestV2 {}
