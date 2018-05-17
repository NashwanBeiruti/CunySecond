package com.tareksaidee.cunysecond;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

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
    private String classID;
    private ProgressBar progressBar;
    private String school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_courses);
        classID = getIntent().getStringExtra("classID");
        school = getIntent().getStringExtra("school");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child(getSchool(school));
        coursesView = (RecyclerView) findViewById(R.id.courses_recycler);
        courses = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        //pushData("queens_college");
        coursesAdapter = new CoursesAdapter(this, courses);
        coursesView.setAdapter(coursesAdapter);
        coursesView.setLayoutManager(layoutManager);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    void attachDatabaseReadListener(String classID) {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Course temp = dataSnapshot.getValue(Course.class);
                    temp.setSchool(mMessagesDatabaseReference.getKey());
                    CourseParent parent = new CourseParent(temp);
                    coursesAdapter.addCourse(parent);
                    progressBar.setVisibility(View.GONE);
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
            mMessagesDatabaseReference.orderByChild("classID").equalTo(classID).addChildEventListener(mChildEventListener);
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
        attachDatabaseReadListener(classID);
    }

    void detachReadListener() {
        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    public void pushData(String school) {
        ArrayList<Course> coursesTemp = new ArrayList<>();
        Course course = new Course();
        course.setCapacity(20);
        course.setClassID("3130");
        course.setCredits(3);
        course.setDays(new ArrayList<String>());
        course.setDepartment("CISC");
        course.setDescription("dsadasdsadasdsadasdast");
        course.setEnrolled(12);
        course.setInstructor("hello");
        course.setStartTime("10:45 AM");
        course.setEndTime("12:15 PM");
        course.setMode("ONLINE");
        List<String> prereqs = new ArrayList<>();
        prereqs.add("CISC 1110");
        course.setPrereqs(prereqs);
        List<String> students = new ArrayList<>();
        students.add("1232131");
        course.setEnrolledStudents(students);
        course.setSemester("Spring");
        course.setYear(2012);
        course.setRoom("232NE");
        ArrayList<String> days = new ArrayList<>();
        days.add("Mon");
        days.add("Tues");
        List<String> books = new ArrayList<>();
        books.add("Intro to Programming");
        course.setTextbooks(books);
        course.setDays(days);
        course.setSchool("queens_college");
        course.setSectionID(12313);
        course.setLevel("undergrad");
        course.setName("intro to programming");
        coursesTemp.add(course);
        coursesTemp.add(course);
        coursesTemp.add(course);
        DatabaseReference classesReference = mFirebaseDatabase.getReference().child(school);
        classesReference.setValue(coursesTemp);
    }

    private String getSchool(String school) {
        switch (school) {
            case "Borough Of Manhattan Community College":
                return "borough_of_manhattan";
            case "Bronx Community College":
                return "bronx_community";
            case "Baruch College":
                return "baruch_college";
            case "Brooklyn College":
                return "brooklyn_college";
            case "Guttman Community College":
                return "guttman_community";
            case "Hostos Community College":
                return "hostos_community";
            case "Kingsborough Community College":
                return "kingsborough_community";
            case "LaGuardia Community College":
                return "laguardia_community";
            case "Queensborough Community College":
                return "queensborough_community";
            case "College of Staten Island":
                return "staten_island";
            case "Hunter College":
                return "hunter_college";
            case "John Jay College Of Criminal Justice":
                return "john_jay";
            case "Lehman College":
                return "lehman_college";
            case "Medgar Evers College":
                return "medgar_evers";
            case "New York City College Of Technology":
                return "city_college_of_tech";
            case "Queens College":
                return "queens_college";
            case "The City College Of New York":
                return "city_college";
            case "CUNY Graduate Center":
                return "graduate_center";
            case "CUNY Graduate School Of Journalism":
                return "school_of_journalism";
            case "CUNY Graduate School Of Public Health And Health Policy":
                return "school_of_public_health";
            case "CUNY School Of Law":
                return "school_of_law";
            case "CUNY School Of Professional Studies":
                return "school_of_professional_studies";
            case "Macaulay Honors College":
                return "macaulay_honors";
            default:
                return "brooklyn_college";
        }
    }

}
