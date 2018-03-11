package com.tareksaidee.cunysecond;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Calendar extends AppCompatActivity implements MonthLoader.MonthChangeListener {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCoursesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mAuthInstance;
    private WeekView mWeekView;
    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    private Map<String, List<WeekViewEvent>> mEventsMap;
    DateTimeFormatter timeFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mEventsMap = new HashMap<>();
        mAuthInstance = FirebaseAuth.getInstance();
        timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCoursesDatabaseReference = mFirebaseDatabase.getReference().child("users")
                .child(mAuthInstance.getCurrentUser().getUid()).child("currentCourses");
        mWeekView = (WeekView) findViewById(R.id.weekView);
        mWeekView.setMonthChangeListener(this);
        //mWeekView.setXScrollingSpeed(0);
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        LocalDate localDate = LocalDate.of(newYear, newMonth, 1);
        while (localDate.getMonthValue() == newMonth) {
            String today = localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US).toLowerCase();
            if (mEventsMap.containsKey(today)) {
                Log.e("check","does");
                events.addAll(mEventsMap.get(today));
            }
            localDate = localDate.plusDays(1);
        }
        Log.e("dsadsada", events.size() + "");
        return events;
    }

    void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    MiniCourse miniCourse = dataSnapshot.getValue(MiniCourse.class);
                    LocalTime startTime = LocalTime.parse(miniCourse.getStartTime(), timeFormatter);
                    LocalTime endTime = LocalTime.parse(miniCourse.getEndTime(), timeFormatter);
                    WeekViewEvent event = new WeekViewEvent(miniCourse.getSectionID(), miniCourse.getName(),
                            miniCourse.getYear(), 1, 1, startTime.getHour(), startTime.getMinute(), miniCourse.getYear(),
                            1, 1, endTime.getHour(), endTime.getMinute());
                    ArrayList<String> days = (ArrayList<String>) miniCourse.getDays();
                    for (String day : days) {
                        if (!mEventsMap.containsKey(day)) {
                            mEventsMap.put(day, new ArrayList<WeekViewEvent>());
                        }
                        mEventsMap.get(day).add(event);
                    }
                    mWeekView.notifyDatasetChanged();
                    Log.e("notify","notify");
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
