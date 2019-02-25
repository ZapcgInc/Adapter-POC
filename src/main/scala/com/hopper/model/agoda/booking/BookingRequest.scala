package com.hopper.model.agoda.booking

import java.io.StringWriter

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlElement, XmlRootElement, XmlTransient}
import javax.xml.bind.{JAXBContext, JAXBException, Marshaller}

@XmlRootElement(name = "BookingRequestV3")
@XmlAccessorType(XmlAccessType.FIELD)
class BookingRequest
{
    @XmlTransient
    private val AGODA_REQUEST_MARSHALLER: Marshaller = JAXBContext.newInstance(classOf[BookingRequest]).createMarshaller

    @XmlAttribute(name = "siteid")
    var siteId: String = "1812488"

    @XmlAttribute(name = "apikey")
    var apiKey: String = "6fae573e-b261-4c02-97b4-3dd20d1e74b2"

    @XmlAttribute(name = "delaybooking")
    var delayBooking: Boolean = true

    @XmlElement(name = "tag")
    var tag: String = "0000-0000-00"

    @XmlElement(name = "BookingDetails")
    var bookingDetails: BookingDetails = _

    @XmlElement(name = "CustomerDetail")
    var customerDetail: CustomerDetail = _

    def this(p_bookingDetails: BookingDetails, p_customerDetail: CustomerDetail)
    {
        this()
        bookingDetails = p_bookingDetails
        customerDetail = p_customerDetail
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
