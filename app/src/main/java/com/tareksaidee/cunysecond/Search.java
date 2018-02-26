package com.tareksaidee.cunysecond;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class Search extends AppCompatActivity {

    EditText classID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        classID = (EditText) findViewById(R.id.class_id_field);

    }

    public void startSearch(View view) {
        if (!classID.getText().toString().trim().equals("")) {
            Intent intent = new Intent(this, EnrollCourses.class);
            intent.putExtra("classID", classID.getText().toString());
            startActivity(intent);
        }
    }
}
