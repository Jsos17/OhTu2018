/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

/**
 *
 * @author jpssilve
 */
public class CourseStats {

    private int students;
    private double hour_total;
    private int exercise_total;

    public void setStudents(int students) {
        this.students = students;
    }

    public void setHour_total(double hour_total) {
        this.hour_total = hour_total;
    }

    public void setExercise_total(int exercise_total) {
        this.exercise_total = exercise_total;
    }

    public int getStudents() {
        return students;
    }

    public double getHour_total() {
        return hour_total;
    }

    public int getExercise_total() {
        return exercise_total;
    }
}
