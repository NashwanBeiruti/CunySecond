package com.tareksaidee.cunysecond;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

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

    public CourseChildViewHolder(View itemView){
        super(itemView);
        sectionID = (TextView) itemView.findViewById(R.id.section_id);
        semester = (TextView) itemView.findViewById(R.id.course_semester);
        mode = (TextView) itemView.findViewById(R.id.course_mode);
        registeredNumb = (TextView) itemView.findViewById(R.id.registered_number);
        room = (TextView) itemView.findViewById(R.id.course_room);
        school = (TextView) itemView.findViewById(R.id.course_school);
    }

    public void bind(Course course){
        sectionID.setText(course.getSectionID() + " ");
        semester.setText(course.getSemester());
        mode.setText(course.getMode());
        registeredNumb.setText(course.getEnrolled() +"/" + course.getCapacity());
        room.setText(course.getRoom());
        school.setText(course.getSchool());
    }
}
