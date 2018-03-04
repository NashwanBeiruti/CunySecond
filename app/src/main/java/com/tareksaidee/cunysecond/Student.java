package com.tareksaidee.cunysecond;

import java.util.List;

/**
 * Created by tarek on 2/27/2018.
 */

public class Student {
    private String firstName;
    private String lastName;
    private String DOB;
    private String street;
    private String city;
    private String zipcode;
    private int totalCredits;
    private List<MiniCourse> courseHistory;
    private List<MiniCourse> currentCourses;
    private double GPA;
    private double moneyDue;
    private String phoneNumber;
    private String school;
    private String major;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public List<MiniCourse> getCourseHistory() {
        return courseHistory;
    }

    public void setCourseHistory(List<MiniCourse> courseHistory) {
        this.courseHistory = courseHistory;
    }

    public List<MiniCourse> getCurrentCourses() {
        return currentCourses;
    }

    public void setCurrentCourses(List<MiniCourse> currentCourses) {
        this.currentCourses = currentCourses;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public double getMoneyDue() {
        return moneyDue;
    }

    public void setMoneyDue(double moneyDue) {
        this.moneyDue = moneyDue;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}


//first name, last name, DOB, street, city, zip code, total credits, list of sections rn, history of classes + grade/credits, money due, phone, GPA