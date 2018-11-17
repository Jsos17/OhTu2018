/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

import java.util.ArrayList;

/**
 *
 * @author jpssilve
 */
public class Course {

    private String name;
    private String fullName;
    private String term;
    private int year;
    private ArrayList<Integer> exercises;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    public void setTerm(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }
    

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setExercises(ArrayList<Integer> exercises) {
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public ArrayList<Integer> getExercises() {
        return exercises;
    }
}
