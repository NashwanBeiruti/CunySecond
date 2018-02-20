package com.tareksaidee.cunysecond;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

/**
 * Created by tarek on 2/19/2018.
 */

public class CourseViewHolder extends ParentViewHolder {

    private TextView deptAndID;
    private TextView name;
    private TextView professor;
    private TextView days;
    private TextView time;
    private TextView credits;

    public CourseViewHolder(View itemView){
        super(itemView);
        deptAndID = (TextView) itemView.findViewById(R.id.dept_and_id);
        name = (TextView) itemView.findViewById(R.id.course_name);
        professor = (TextView) itemView.findViewById(R.id.prof_name);
        days = (TextView) itemView.findViewById(R.id.course_days);
        time = (TextView) itemView.findViewById(R.id.course_time);
        credits = (TextView) itemView.findViewById(R.id.course_credits);
    }

    public void bind(CourseParent courseParent){
        Course course = courseParent.getChildList().get(0);
        deptAndID.setText(course.getDepartment() + " " + course.getClassID());
        name.setText(course.getName());
        professor.setText(course.getInstructor());
        String daysString = "";
        for (String day:course.getDays())
            daysString = daysString.concat(day + " ");
        days.setText(daysString);
        time.setText(course.getStartTime().toString() + "-" + course.getEndTime());
        credits.setText(course.getCredits() + "");
    }
}
