package com.tareksaidee.cunysecond;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.List;

/**
 * Created by tarek on 2/19/2018.
 */

public class CoursesAdapter extends ExpandableRecyclerAdapter<CourseParent, Course,CourseViewHolder, CourseChildViewHolder> {

    private LayoutInflater mInflater;


    public CoursesAdapter(Context context, @NonNull List<CourseParent> courses){
        super(courses);
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View parentView = mInflater.inflate(R.layout.course_item, parentViewGroup,false);
        return new CourseViewHolder(parentView);
    }

    @NonNull
    @Override
    public CourseChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View childView = mInflater.inflate(R.layout.course_item_child, childViewGroup,false);
        return new CourseChildViewHolder(childView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull CourseViewHolder parentViewHolder, int parentPosition, @NonNull CourseParent parent) {
        parentViewHolder.bind(parent);
        if(parentPosition==0)
            parentViewHolder.hideDivider();
    }

    @Override
    public void onBindChildViewHolder(@NonNull CourseChildViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull Course child) {
        childViewHolder.bind(child);
    }

    public void addCourse(CourseParent courseParent){
        getParentList().add(courseParent);
        notifyParentDataSetChanged(false);
    }

    void clear() {
        getParentList().clear();
    }

}
