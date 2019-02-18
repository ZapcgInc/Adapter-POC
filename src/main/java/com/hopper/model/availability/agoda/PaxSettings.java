package com.hopper.model.availability.agoda;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="PaxSettings")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaxSettings {
    @XmlAttribute(name="submit")
    private String submit;
    @XmlAttribute(name="infantage")
    private int infantage;
    @XmlAttribute(name="childage")
    private int childage;

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public int getInfantage() {
        return infantage;
    }

    public void setInfantage(int infantage) {
        this.infantage = infantage;
    }

    public int getChildage() {
        return childage;
    }

    public void setChildage(int childage) {
        this.childage = childage;
    }

    @Override
    public String toString() {
        return "PaxSettings{" +
                "submit='" + submit + '\'' +
                ", infantage=" + infantage +
                ", childage=" + childage +
                '}';
    }
}
