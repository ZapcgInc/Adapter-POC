@javax.xml.bind.annotation.XmlSchema(
        namespace = "http://xml.agoda.com",
        elementFormDefault= XmlNsForm.QUALIFIED, attributeFormDefault=XmlNsForm.UNQUALIFIED ,
        xmlns = {
                @XmlNs(namespaceURI = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi")})
package com.hopper.model.availability.agoda;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;