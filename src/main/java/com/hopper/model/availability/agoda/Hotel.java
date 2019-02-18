package com.hopper.model.availability.agoda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Hotel")
@XmlAccessorType(XmlAccessType.FIELD)
public class Hotel {

    @XmlElement(name = "Id")
    private int id;

    @XmlElement(name = "CheapestRoom")
    private CheapestRoom cheapestRoom;

    @XmlElement(name = "PaxSettings")
    private PaxSettings paxSettings;

    @XmlElement(name = "Rooms")
    private Rooms rooms;

    public PaxSettings getPaxSettings() {
        return paxSettings;
    }

    public void setPaxSettings(PaxSettings paxSettings) {
        this.paxSettings = paxSettings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public CheapestRoom getCheapestRoom() {
        return cheapestRoom;
    }

    public void setCheapestRoom(CheapestRoom cheapestRoom) {
        cheapestRoom = cheapestRoom;
    }

    public Rooms getRoom() {
        return rooms;
    }

    public void setRoom(Rooms room) {
        this.rooms = room;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "Id=" + id +
                ", CheapestRoom=" + cheapestRoom +
                ", paxSettings=" + paxSettings +
                ", room=" + rooms +
                '}';
    }
}
