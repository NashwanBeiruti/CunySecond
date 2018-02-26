package com.tareksaidee.cunysecond;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Search extends AppCompatActivity {

    EditText classID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        classID = (EditText) findViewById(R.id.class_id_field);

    }
}
