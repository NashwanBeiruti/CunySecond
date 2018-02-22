package com.tareksaidee.cunysecond;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EnrollCourses extends AppCompatActivity {

    RecyclerView coursesView;
    CoursesAdapter coursesAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<CourseParent> courses;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_courses);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("brooklyn_college");
        coursesView = (RecyclerView) findViewById(R.id.courses_recycler);
        courses = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        //getData();
        coursesAdapter = new CoursesAdapter(this,courses);
        coursesView.setAdapter(coursesAdapter);
        coursesView.setLayoutManager(layoutManager);
    }

    void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Course temp = dataSnapshot.getValue(Course.class);
                    CourseParent parent = new CourseParent(temp);
                    coursesAdapter.addCourse(parent);
                    Log.d("whatever", temp.toString());
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        detachReadListener();
        coursesAdapter.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        attachDatabaseReadListener();
    }

    void detachReadListener() {
        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

//    public void getData(){
//        Course course = new Course();
//        course.setCapacity(20);
//        course.setClassID("1010");
//        course.setCredits(3);
//        course.setDays(new ArrayList<String>());
//        course.setDepartment("CISC");
//        course.setDescription("dsadasdsadasdsadasdast");
//        course.setEnrolled(12);
//        course.setInstructor("hello");
//        course.setStartTime(LocalTime.now());
//        course.setEndTime(LocalTime.now());
//        course.setMode("ONLINE");
//        course.setPrereqs(new ArrayList<String>());
//        course.setSemester("Spring");
//        course.setYear(2012);
//        course.setRoom("232NE");
//        ArrayList<String> days = new ArrayList<>();
//        days.add("Mon");
//        days.add("Tues");
//        course.setDays(days);
//        course.setSchool("brooklyn college");
//        course.setSectionID(12313);
//        course.setName("intro to programming");
//        ArrayList<Course> list = new ArrayList<>();
//        list.add(course);
//        CourseParent parent = new CourseParent(list);
//        courses.add(parent);
//        courses.add(parent);
//    }

}
