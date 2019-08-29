package ohtu;

import java.util.ArrayList;

public class Submission {

    private int week;
    private int hours;
    private ArrayList<Integer> exercises;
    private String course;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getHours() {
        return hours;
    }

    public ArrayList<Integer> getExercises() {
        return exercises;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setExercises(ArrayList<Integer> exercises) {
        this.exercises = exercises;
    }

    public int getWeek() {
        return week;
    }

    @Override
    public String toString() {
        return "viikko " + week + ":";
    }
}
