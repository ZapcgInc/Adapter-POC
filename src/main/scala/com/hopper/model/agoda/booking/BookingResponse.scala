package com.hopper.model.agoda.booking

import java.io.StringReader

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlElement, XmlElementWrapper, XmlRootElement, XmlTransient}
import javax.xml.bind.{JAXBContext, Unmarshaller}

@XmlRootElement(name = "BookingResponseV3")
@XmlAccessorType(XmlAccessType.FIELD)
class BookingResponse
{
    @XmlTransient
    private val AGODA_RESPONSE_UNMARSHALLER: Unmarshaller = JAXBContext.newInstance(classOf[BookingResponse]).createUnmarshaller()

    @XmlAttribute(name = "status")
    var status: Int = _

    @XmlElement(name = "BookingDetails")
    var details: BookingResponseDetails = _

    @XmlElementWrapper(name = "ErrorMessages")
    @XmlElement(name = "ErrorMessage")
    var errors: Array[ErrorMessage] = _

    def unmarshall(responseAsString: String): BookingResponse =
    {
        AGODA_RESPONSE_UNMARSHALLER
          .unmarshal(new StringReader(responseAsString))
          .asInstanceOf[BookingResponse]
    }


    override def toString = s"BookingResponse(AGODA_RESPONSE_UNMARSHALLER=$AGODA_RESPONSE_UNMARSHALLER, status=$status, details=$details, errors=$errors)"
}

object BookingResponse extends BookingResponse
{}
