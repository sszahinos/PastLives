package com.practica.pastlives;

import java.io.Serializable;
import java.util.Date;

public class PastLife implements Serializable {
    String name;
    Date date;
    int animal;
    String place;
    int gift;

    public PastLife(String name, Date date, int animal, String place, int gift) {
        this.name = name;
        this.animal = animal;
        this.place = place.toUpperCase();
        this.gift = gift;
        this.date = date;
    }

    @Override
    public String toString() {
        return "PastLife{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", animal='" + animal + '\'' +
                ", place='" + place + '\'' +
                ", gift='" + gift + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public int getAnimal() {
        return animal;
    }

    public String getPlace() {
        return place;
    }

    public int getGift() {
        return gift;
    }
}
