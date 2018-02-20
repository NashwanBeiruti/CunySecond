package com.tareksaidee.cunysecond;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EnrollCourses extends AppCompatActivity {

    RecyclerView coursesView;
    CoursesAdapter coursesAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<CourseParent> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_courses);
        coursesView = (RecyclerView) findViewById(R.id.courses_recycler);
        courses = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        getData();
        coursesAdapter = new CoursesAdapter(this,courses);
        coursesView.setAdapter(coursesAdapter);
        coursesView.setLayoutManager(layoutManager);

    }

    public void getData(){
        Course course = new Course();
        course.setCapacity(20);
        course.setClassID("1010");
        course.setCredits(3);
        course.setDays(new ArrayList<String>());
        course.setDepartment("CISC");
        course.setDescription("dsadasdsadasdsadasdast");
        course.setEnrolled(12);
        course.setInstructor("hello");
        course.setStartTime(LocalTime.now());
        course.setEndTime(LocalTime.now());
        course.setMode("ONLINE");
        course.setPrereqs(new ArrayList<String>());
        course.setSemester("Spring");
        course.setYear(2012);
        course.setRoom("232NE");
        ArrayList<String> days = new ArrayList<>();
        days.add("Mon");
        days.add("Tues");
        course.setDays(days);
        course.setSchool("brooklyn college");
        course.setSectionID(12313);
        course.setName("intro to programming");
        ArrayList<Course> list = new ArrayList<>();
        list.add(course);
        CourseParent parent = new CourseParent(list);
        courses.add(parent);
        courses.add(parent);
    }

}
