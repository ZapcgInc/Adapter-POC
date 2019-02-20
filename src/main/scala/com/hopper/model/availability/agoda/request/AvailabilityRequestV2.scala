package com.hopper.model.availability.agoda.request

import com.hopper.constants.GlobalConstants
import com.hopper.model.availability.agoda.request.constants.AgodaAvailabilityRequestType
import com.twitter.finagle.http.Request
import javax.xml.bind.annotation._
import org.apache.commons.lang.StringUtils

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

@XmlRootElement(name = "AvailabilityRequestV2")
@XmlAccessorType(XmlAccessType.FIELD)
class AvailabilityRequestV2
{
    @XmlAttribute(name = "siteid")
    val siteId:String = "1812488"

    @XmlAttribute(name = "apikey")
    val apiKey:String  = "6fae573e-b261-4c02-97b4-3dd20d1e74b2"

    @XmlElement(name = "Type")
    var requestType:Int  = _

    @XmlElement(name = "Id")
    var propertyIds:String  = _

    @XmlElement(name = "CheckIn")
    var checkInDate:String  = _

    @XmlElement(name = "CheckOut")
    var checkOutDate:String  = _

    @XmlElement(name = "Rooms")
    var roomCount:Int = _

    @XmlElement(name = "Adults")
    var adultsCount:Int = _

    @XmlElement(name = "Children")
    var childrenCount:Int = _

    @XmlTransient
    var childrenAges:List[Int] = _

    @XmlElement(name = "Language")
    var language:String = _

    @XmlElement(name = "Currency")
    var currency:String = _

    @XmlTransient
    var occupancy:List[String] = _

    def this(request: Request)
    {
        this()
        _populateRequestInfo(request)
        _populatePropertyID(request)
    }

    def _populateRequestInfo(request: Request) : Unit =
    {
        // TODO : I think this should be headers.
        for (entry <- request.getParams().iterator())
        {
            val k = entry.getKey
            val v = entry.getValue
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

                case _ =>
            }
        }
    }

    def _populatePropertyID(request: Request): Unit =
    {
        propertyIds = request.getParams(GlobalConstants.PROPERTY_ID).asScala.toList.mkString(",")
        requestType = if (request.getParams(GlobalConstants.PROPERTY_ID).size() == 1) AgodaAvailabilityRequestType.HotelSearch.id else AgodaAvailabilityRequestType.HotelListSearch.id
    }

    def _populateOccupancy(reqOccupancy: String): Unit =
    {
        if (StringUtils.isEmpty(reqOccupancy))
        {
            return
        }

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
