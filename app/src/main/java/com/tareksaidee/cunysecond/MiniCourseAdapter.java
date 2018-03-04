package com.tareksaidee.cunysecond;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarek on 3/2/2018.
 */

public class MiniCourseAdapter extends RecyclerView.Adapter<MiniCourseAdapter.MiniCourseViewHolder>{

    private List<MiniCourse> miniCourses;
    private Context mContext;

    MiniCourseAdapter(@NonNull Context context) {
        mContext = context;
        miniCourses = new ArrayList<>();
    }

    @Override
    public MiniCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mini_course_item, parent, false);
        return new MiniCourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MiniCourseViewHolder holder, int position) {
        holder.bind(miniCourses.get(position));
        if(position==0)
            holder.hideDivider();
    }

    @Override
    public int getItemCount() {
        return miniCourses.size();
    }

    public void setMiniCourses(List<MiniCourse> miniCourses) {
        this.miniCourses = miniCourses;
        notifyDataSetChanged();
    }

    public void clear() {
        miniCourses.clear();
    }

    class MiniCourseViewHolder extends RecyclerView.ViewHolder {

        TextView courseName;
        TextView courseDeptID;
        TextView instructor;
        TextView room;
        TextView days;
        TextView time;
        TextView creditsGrade;
        TextView semester;
        TextView divider;

        MiniCourseViewHolder(View view) {
            super(view);
            courseDeptID = (TextView) view.findViewById(R.id.dept_and_id);
            courseName = (TextView) view.findViewById(R.id.course_name);
            instructor = (TextView) view.findViewById(R.id.prof_name);
            room = (TextView) view.findViewById(R.id.course_room);
            days = (TextView) view.findViewById(R.id.course_days);
            time = (TextView) view.findViewById(R.id.course_time);
            creditsGrade = (TextView) view.findViewById(R.id.course_credits);
            semester = (TextView) view.findViewById(R.id.course_semester);
            divider = (TextView) view.findViewById(R.id.course_divider);
        }

        public void bind(MiniCourse miniCourse){
            courseDeptID.setText(miniCourse.getCourseDeptID());
            courseName.setText(miniCourse.getName());
            instructor.setText(miniCourse.getInstructor());
            room.setText(miniCourse.getRoom());
            days.setText("");
            for(String s:miniCourse.getDays()){
                days.append(s + " ");
            }
            time.setText(miniCourse.getStartTime() + "-" + miniCourse.getEndTime());
            creditsGrade.setText(miniCourse.getCredits() + " Credits, Grade: " + miniCourse.getGrade());
            semester.setText(miniCourse.getSemester() +" "+miniCourse.getYear());
        }

        public void hideDivider(){
            divider.setVisibility(View.GONE);
        }
    }
}
