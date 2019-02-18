package com.hopper.model.availability.agoda;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="PolicyText")
@XmlAccessorType(XmlAccessType.FIELD)
public class PolicyText {

    @XmlAttribute
    private String language;

    @XmlValue
    private String content;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PolicyText{" +
                "language='" + language + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
