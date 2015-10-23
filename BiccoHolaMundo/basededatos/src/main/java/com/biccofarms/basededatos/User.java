package com.biccofarms.basededatos;

/**
 * Created by julian on 10/19/15.
 */
public class User {

    long id;
    String name;
    String drink;
    String sport;

    public User(long id, String name, String drink, String sport) {
        this.id = id;
        this.name = name;
        this.drink = drink;
        this.sport = sport;
    }

    public User(String name, String drink, String sport) {
        this.name = name;
        this.drink = drink;
        this.sport = sport;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
}
