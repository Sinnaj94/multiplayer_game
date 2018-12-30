package com.game.gameworld;

public class Time {
    int hours;
    int minutes;
    int days;

    public Time() {
        hours = 12;
        minutes = 00;
    }

    public void tick() {
        if (minutes + 1 >= 60) {
            minutes = 0;
            hours++;
            if (hours + 1 >= 24) {
                hours = 0;
                days++;
            }
        } else {
            minutes++;
        }
    }

    @Override
    public String toString() {
        return String.format("It is %d:%d", hours, minutes);
    }
}
