package com.tareksaidee.cunysecond;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class Search extends AppCompatActivity {

    private EditText classID;
    private Spinner schoolsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        classID = (EditText) findViewById(R.id.class_id_field);
        schoolsSpinner = (Spinner) findViewById(R.id.school_drop_down);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.schools, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schoolsSpinner.setAdapter(adapter);
        schoolsSpinner.setSelection(3);
    }

    public void startSearch(View view) {
        if (!classID.getText().toString().trim().equals("")) {
            Intent intent = new Intent(this, EnrollCourses.class);
            intent.putExtra("classID", classID.getText().toString());
            startActivity(intent);
        }
    }
}
