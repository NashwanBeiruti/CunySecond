package com.tareksaidee.cunysecond;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAccount extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private String userUID;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserDatabaseReference;
    private ChildEventListener mChildEventListener;
    private MiniCourseAdapter currentCoursesAdapter;
    private MiniCourseAdapter historyAdapter;
    private TextView userNameView;
    private TextView userDOB;
    private TextView userAddress;
    private TextView userPhone;
    private TextView userGPA;
    private TextView userCredits;
    private TextView userMoneyDue;
    private TextView userMajor;
    private RecyclerView currentCoursesView;
    private RecyclerView pastCoursesView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        mFirebaseAuth = FirebaseAuth.getInstance();
        userNameView = (TextView) findViewById(R.id.user_name);
        userDOB = (TextView) findViewById(R.id.user_dob);
        userAddress = (TextView) findViewById(R.id.user_address);
        userPhone = (TextView) findViewById(R.id.user_phone);
        userGPA = (TextView) findViewById(R.id.user_gpa);
        userCredits = (TextView) findViewById(R.id.user_credits);
        userMoneyDue = (TextView) findViewById(R.id.user_money_due);
        userMajor = (TextView) findViewById(R.id.user_major);
        currentCoursesView = (RecyclerView) findViewById(R.id.student_current_courses);
        pastCoursesView = (RecyclerView) findViewById(R.id.student_course_history);
        userUID = mFirebaseAuth.getCurrentUser().getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserDatabaseReference = mFirebaseDatabase.getReference().child("users");
        currentCoursesAdapter = new MiniCourseAdapter(this);
        historyAdapter = new MiniCourseAdapter(this);
        pastCoursesView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        currentCoursesView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        currentCoursesView.setAdapter(currentCoursesAdapter);
        pastCoursesView.setAdapter(historyAdapter);

    }

    void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Student student = dataSnapshot.getValue(Student.class);
                    userNameView.setText("Name: " + student.getFirstName() + " " + student.getLastName());
                    userDOB.setText("DOB: " + student.getDOB());
                    userAddress.setText("Address: " + student.getStreet() + "\n" + student.getCity()
                        + ", NY " + student.getZipcode());
                    userPhone.setText("Phone #: " + student.getPhoneNumber());
                    userGPA.setText("GPA: " + student.getGPA());
                    userCredits.setText("Credits: " + student.getTotalCredits());
                    userMoneyDue.setText("Money Due: " + student.getMoneyDue());
                    userMajor.setText("Major: " + student.getMajor());
                    currentCoursesAdapter.setMiniCourses(student.getCurrentCourses());
                    historyAdapter.setMiniCourses(student.getCourseHistory());
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
            mUserDatabaseReference.orderByKey().equalTo(userUID).addChildEventListener(mChildEventListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        detachReadListener();
        historyAdapter.clear();
        currentCoursesAdapter.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        attachDatabaseReadListener();
    }

    void detachReadListener() {
        if (mChildEventListener != null) {
            mUserDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }
}
