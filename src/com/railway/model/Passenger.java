package com.railway.model;

public class Passenger {
    private final String name;
    private final int age;
    private final String gender;

    public Passenger(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return String.format("%s (%d, %s)", name, age, gender);
    }
}
