package com.hopper.model.availability.agoda

import com.twitter.finagle.http.Request
import com.hopper.constants.GlobalConstants
import javax.xml.bind.annotation._
import org.apache.commons.lang.StringUtils

import scala.collection.JavaConverters._


@XmlRootElement(name = "AvailabilityRequestV2")
@XmlAccessorType(XmlAccessType.FIELD)
class AvailabilityRequest(val request: Request)
{
    @XmlAttribute(name = "siteid")
    private var siteId = "1812488"

    @XmlAttribute(name = "apikey")
    private var apiKey = "6fae573e-b261-4c02-97b4-3dd20d1e74b2"

    @XmlAttribute(name = "xmlns")
    private var xmlns = "http://xml.agoda.com"

    @XmlAttribute(name = "xmlns:xsi")
    private var xmlnsXsi = "http://www.w3.org/2001/XMLSchema-instance"

    @XmlElement(name = "Type")
    private var requestType = _

    @XmlElement(name = "Id")
    private var propertyIds = _

    @XmlElement(name = "CheckIn")
    private var checkInDate = _

    @XmlElement(name = "CheckOut")
    private var checkOutDate = _

    @XmlElement(name = "Rooms")
    private var roomCount = _

    @XmlElement(name = "Adults")
    private var adultsCount = _

    @XmlElement(name = "Children")
    private var childrenCount = _

    @XmlTransient
    private var childrenAges = _

    @XmlElement(name = "Language")
    private var language = _

    @XmlElement(name = "Currency")
    private var currency = _

    @XmlTransient
    private var occupancy = _

    def this()
    {
        this(null)
    }

    create()

    def create(): Unit =
    {
        if (request == null)
        {
            return
        }

        _populateRequestInfo()
        _populatePropertyID()
    }

    def _populateRequestInfo() : Unit =
    {
        for ((k: String, v: String) <- request.getParams())
        {
            k match
            {
                case GlobalConstants.CHECKIN_PARAM_KEY =>
                {
                    checkInDate = v
                }
                case GlobalConstants.CHECKOUT_PARAM_KEY =>
                {
                    checkOutDate = v
                }
                case GlobalConstants.CURRENCY_CODE_KEY =>
                {
                    currency = v
                }

                case GlobalConstants.LANGUAGE_CODE_KEY =>
                {
                    language = v
                }

                case GlobalConstants.OCCUPANCY_KEY =>
                {
                    _populateOccupancy(v)
                }
            }
        }
    }

    def _populatePropertyID(): Unit =
    {
        import com.hopper.model.availability.agoda.constants.AgodaAvailabilityRequestType
        propertyIds = request.getParams(GlobalConstants.PROPERTY_ID).asScala.toList.mkString(",")
        requestType = if (request.getParams(GlobalConstants.PROPERTY_ID).size() == 1) AgodaAvailabilityRequestType.HotelSearch.id else AgodaAvailabilityRequestType.HotelListSearch.id
    }

    def _populateOccupancy(reqOccupancy: String): Unit =
    {
        if (StringUtils.isNotEmpty(reqOccupancy))
            return

        val roomList = reqOccupancy.split("\\|").toList

        occupancy = roomList
        roomCount = roomList.size

        for (room <- roomList)
        {
            val split = reqOccupancy.split("-")
            adultsCount = split(0).toInt
            if (split.length == 2)
            {
                childrenAges = split(1).split(",").map(_.toInt).toList
                childrenCount = childrenAges.size
            }
        }
    }
}
