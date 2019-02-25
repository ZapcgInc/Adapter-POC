package com.hopper.model.agoda.availability.response

import java.io.StringReader

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlElement, XmlElementWrapper, XmlRootElement, XmlTransient}
import javax.xml.bind.{JAXBContext, Unmarshaller}

@XmlRootElement(name = "AvailabilityLongResponseV2")
@XmlAccessorType(XmlAccessType.FIELD)
class AvailabilityLongResponseV2
{
    @XmlTransient
    private val AGODA_RESPONSE_UNMARSHALLER: Unmarshaller = JAXBContext.newInstance(classOf[AvailabilityLongResponseV2]).createUnmarshaller()

    @XmlAttribute(name = "status")
    var status: Int = 0

    @XmlAttribute(name = "searchid")
    var searchID: String = _

    @XmlElementWrapper(name = "Hotels")
    @XmlElement(name = "Hotel")
    var hotels: Array[Hotel] = _

    @XmlElementWrapper(name = "ErrorMessages")
    @XmlElement(name = "ErrorMessage")
    var errors: Array[ErrorMessage] = _

    def unmarshall(responseAsString: String): AvailabilityLongResponseV2 =
    {

        AGODA_RESPONSE_UNMARSHALLER
          .unmarshal(new StringReader(responseAsString))
          .asInstanceOf[AvailabilityLongResponseV2]
    }

    override def toString = s"AvailabilityLongResponseV2($status, $searchID, $hotels)"
}

object AvailabilityLongResponseV2 extends AvailabilityLongResponseV2{}