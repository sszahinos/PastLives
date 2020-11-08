package com.practica.pastlives;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ResultCalculator {
    //private ArrayList<String> persons, features, actions, deaths;
    private String name;
    private Context context;
    private int racePos;
    private String race;
    private Drawable d;
    private String feature;
    private int age;
    private String death;


    public ResultCalculator(PastLife pl, Context context) {
        this.context = context;
        this.name = invertName(pl.getName());
        this.age = calculateYear(pl.getDate());
        this.race = setRace(pl.getAnimal());
        this.d = setRaceImage();
        this.feature = setFeature(pl.getGift());
        this.death = setDeath(pl.getPlace());
    }

    public String generateText() {
        String text = "";
        text = context.getResources().getString(R.string.story, name, race, feature, age, death);
        return text;
    }

    private int calculateYear(Date date) {
        int year;
        int currentYear;

        Calendar calendar = Calendar.getInstance();
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        calendar.setTime(date);
        year = calendar.get(Calendar.YEAR);

        if ((currentYear - year) < 18) {
            year = 18;
        } else {
            year = ((currentYear - year) * 3/2) + 2;
        }

        return year;
    }

    private String invertName(String name) {
        name = name.toLowerCase();
        char[] nameArr = name.toCharArray();

        for (int i = 0; i < nameArr.length/2; i++) {
            char aux = nameArr[i];
            nameArr[i] = nameArr[nameArr.length - i - 1];
            nameArr[nameArr.length -i -1] = aux;
        }
        nameArr[0] = Character.toUpperCase(nameArr[0]);
        name = String.valueOf(nameArr);
        return name;
    }

    private String setDeath(String place) {
        int position = -1;
        switch (place) {
            case "MOUNTAIN":
                position = 0;
                break;
            case "RAINFOREST":
                position = 1;
                break;
            case "BEACH":
                position = 2;
                break;
            case "DESERT":
                position = 3;
                break;
            default:
                break;
        }

        List<String> deaths = Arrays.asList(
                context.getResources().getStringArray(R.array.death));
        System.out.println(deaths.get(position));

        return deaths.get(position);
    }

    private String setFeature(int giftID) {
        int position = -1;
        switch (giftID) {
            case R.id.bRadio1:
                position = 0;
                break;
            case R.id.bRadio2:
                position = 1;
                break;
            case R.id.bRadio3:
                position = 2;
                break;
            default:
                break;
        }

        List<String> deaths = Arrays.asList(
                context.getResources().getStringArray(R.array.features));
        System.out.println(deaths.get(position));

        return deaths.get(position);
    }

    private String setRace(int animal) {

        racePos = -1;
        switch (animal) {
            case 0:
                racePos = 0;
                break;
            case 1:
                racePos = 1;
                break;
            case 2:
                racePos = 2;
                break;
            case 3:
                racePos = 3;
            default:
                break;
        }

        List<String> deaths = Arrays.asList(
                context.getResources().getStringArray(R.array.races));
        System.out.println(deaths.get(racePos));

        return deaths.get(racePos);
    }

    private Drawable setRaceImage() {
        d = null;

        switch (racePos) {
            case 0:
                d = ContextCompat.getDrawable(context, R.drawable.cthulhu);
                //d = context.getDrawable(R.drawable.beach);
                break;
            case 1:
                d = ContextCompat.getDrawable(context, R.drawable.anunnaki);
                //d = context.getDrawable(R.drawable.beach);
                break;
            case 2:
                d = ContextCompat.getDrawable(context, R.drawable.urukhai);
                //d = context.getDrawable(R.drawable.beach);
                break;
            case 3:
                d = ContextCompat.getDrawable(context, R.drawable.nito);
                //d = context.getDrawable(R.drawable.beach);
                break;
            default:
                break;
        }

        return d;
    }

    public String getName() {
        return name;
    }

    public String getRace() {
        return race;
    }

    public Drawable getRaceImage() {
        return d;
    }

    public String getFeature() {
        return feature;
    }

    public int getAge() {
        return age;
    }

    public String getDeath() {
        return death;
    }
}
