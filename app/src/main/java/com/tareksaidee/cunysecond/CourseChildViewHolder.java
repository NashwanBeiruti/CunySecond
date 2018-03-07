package com.tareksaidee.cunysecond;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by tarek on 2/19/2018.
 */

public class CourseChildViewHolder extends ChildViewHolder {

    private TextView sectionID;
    private TextView semester;
    private TextView mode;
    private TextView registeredNumb;
    private TextView room;
    private TextView school;
    private Button courseAction;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsersDatabaseRef;
    private FirebaseAuth mFirebaseAuth;
    private Course myCourse;
    private Student myStudent;

    public CourseChildViewHolder(View itemView) {
        super(itemView);
        sectionID = (TextView) itemView.findViewById(R.id.section_id);
        semester = (TextView) itemView.findViewById(R.id.course_semester);
        mode = (TextView) itemView.findViewById(R.id.course_mode);
        registeredNumb = (TextView) itemView.findViewById(R.id.registered_number);
        room = (TextView) itemView.findViewById(R.id.course_room);
        school = (TextView) itemView.findViewById(R.id.course_school);
        courseAction = (Button) itemView.findViewById(R.id.enroll_button);
        courseAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myStudent == null)
                    Toast.makeText(view.getContext(), "Student not found", Toast.LENGTH_SHORT).show();
                else {
                    if (isEnrolled()) {
                        List<MiniCourse> list = myStudent.getCurrentCourses();
                        for (int i=0;i<list.size();i++)
                            if (list.get(i).getSectionID() == myCourse.getSectionID()) {
                                list.remove(i);
                                break;
                            }
                        myStudent.setCurrentCourses(list);
                        mUsersDatabaseRef.setValue(myStudent);
                    } else {
                        List<MiniCourse> list = myStudent.getCurrentCourses();
                        list.add(new MiniCourse(myCourse));
                        myStudent.setCurrentCourses(list);
                        mUsersDatabaseRef.setValue(myStudent);
                    }
                    myStudent = null;
                }
            }
        });
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void bind(final Course course) {
        sectionID.setText(course.getSectionID() + " ");
        semester.setText(course.getSemester());
        mode.setText(course.getMode());
        registeredNumb.setText(course.getEnrolled() + "/" + course.getCapacity());
        room.setText(course.getRoom());
        school.setText(course.getSchool());
        myCourse = course;
        mUsersDatabaseRef = mFirebaseDatabase.getReference().child("users").child(mFirebaseAuth.getCurrentUser().getUid());
        mUsersDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myStudent = dataSnapshot.getValue(Student.class);
                updateState();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean isEnrolled() {
        List<MiniCourse> list = myStudent.getCurrentCourses();
        for (MiniCourse mCourse : list)
            if (mCourse.getSectionID() == myCourse.getSectionID())
                return true;
        return false;
    }

    private void updateState() {
        if (isEnrolled()) {
            courseAction.setText("DROP");
            courseAction.setBackgroundColor(courseAction.getContext().getResources().getColor(R.color.dropColor));
        } else {
            courseAction.setText("ENROLL");
            courseAction.setBackgroundColor(courseAction.getContext().getResources().getColor(R.color.enrollColor));
        }
    }
}

//put all my database stuff here through an onclick listener. I should store the course in onbind so
//i can refer to it later when i need to add a course info to the user's account.
