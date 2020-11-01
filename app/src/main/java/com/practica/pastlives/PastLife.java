package com.practica.pastlives;

import java.util.Date;

public class PastLife {
    String name;
    int age;
    String animal;
    String place;
    String gift;

    public PastLife(String name, Date date, String animal, String place, String gift) {
        this.name = name;
        this.animal = animal;
        this.place = place;
        this.gift = gift;
        //TODO
        //age = calcAge(date);
    }

}
