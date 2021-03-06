package com.tareksaidee.cunysecond;

import java.util.List;

/**
 * Created by tarek on 2/27/2018.
 */
public class MiniCourse {
    private String name;
    private String startTime;
    private String endTime;
    private int credits;
    private String room;
    private String grade;
    private long sectionID;
    private String semester;
    private int year;
    private List<String> days;
    private String instructor;
    private String school;
    private String courseDeptID;

    public MiniCourse(){}

    public MiniCourse(Course course){
        name = course.getName();
        startTime = course.getStartTime();
        endTime = course.getEndTime();
        credits = course.getCredits();
        room = course.getRoom();
        grade = "IP";
        sectionID = course.getSectionID();
        semester = course.getSemester();
        year = course.getYear();
        days = course.getDays();
        instructor = course.getInstructor();
        school = course.getSchool();
        courseDeptID = course.getDepartment() + " " + course.getClassID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public long getSectionID() {
        return sectionID;
    }

    public void setSectionID(long sectionID) {
        this.sectionID = sectionID;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCourseDeptID() {
        return courseDeptID;
    }

    public void setCourseDeptID(String courseDeptID) {
        this.courseDeptID = courseDeptID;
    }
}