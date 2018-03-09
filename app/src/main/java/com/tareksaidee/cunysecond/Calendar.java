package com.tareksaidee.cunysecond;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Calendar extends AppCompatActivity implements  MonthLoader.MonthChangeListener{

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCoursesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mAuthInstance;
    private WeekView mWeekView;
    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    DateTimeFormatter timeFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mAuthInstance = FirebaseAuth.getInstance();
        timeFormatter  = DateTimeFormatter.ofPattern("hh:mm a");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCoursesDatabaseReference = mFirebaseDatabase.getReference().child("users")
                .child(mAuthInstance.getCurrentUser().getUid()).child("currentCourses");
        mWeekView = (WeekView) findViewById(R.id.weekView);
        mWeekView.setMonthChangeListener(this);
        mWeekView.goToDate(new GregorianCalendar(2018,9,22));
        //mWeekView.setXScrollingSpeed(0);


    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        WeekViewEvent event = new WeekViewEvent(123,"Class",2018,4,10,1,0,2018,4,10,3,0);
        events.add(event);
        return events;
    }

    void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    MiniCourse miniCourse = dataSnapshot.getValue(MiniCourse.class);
//                    LocalTime startTime = LocalTime.parse(miniCourse.getStartTime(),timeFormatter);
//                    WeekViewEvent event = new WeekViewEvent(miniCourse.getSectionID(),miniCourse.getName(),
//                            miniCourse.getYear(),1,28,startTime.getHour(),startTime.getMinute(),miniCourse.getYear(),
//                            1,28,)
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
            mCoursesDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        detachReadListener();
        events.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        attachDatabaseReadListener();
    }

    void detachReadListener() {
        if (mChildEventListener != null) {
            mCoursesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }


}
