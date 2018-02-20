package com.tareksaidee.cunysecond;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarek on 2/19/2018.
 */

public class CourseParent implements Parent<Course> {

    private  List<Course> courses;

    public CourseParent(List<Course> courses){
        this.courses = courses;
    }

    @Override
    public List<Course> getChildList() {
        return courses;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
