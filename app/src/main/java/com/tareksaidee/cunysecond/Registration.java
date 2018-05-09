package com.tareksaidee.cunysecond;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private Spinner schoolsSpinner;
    private DatePicker dob;
    private EditText phone;
    private EditText city;
    private EditText street;
    private EditText zipcode;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsersDatabaseRef;
    private Button submitButton;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        schoolsSpinner = (Spinner) findViewById(R.id.school_drop_down);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.schools, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schoolsSpinner.setAdapter(adapter);
        mFirebaseAuth = FirebaseAuth.getInstance();
        dob = (DatePicker) findViewById(R.id.user_dob);
        phone = (EditText) findViewById(R.id.user_phone);
        city = (EditText) findViewById(R.id.user_city);
        street = (EditText) findViewById(R.id.user_street);
        zipcode = (EditText) findViewById(R.id.user_zip);
        submitButton = (Button) findViewById(R.id.user_info_button);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUsersDatabaseRef = mFirebaseDatabase.getReference().child("users").child(mFirebaseAuth.getCurrentUser().getUid());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = new Student();
                student.setCity(city.getText().toString());
                student.setDOB(dob.getMonth()+"/"+dob.getDayOfMonth()+"/"+dob.getYear());
                String fullName = mFirebaseAuth.getCurrentUser().getDisplayName();
                student.setFirstName(fullName.substring(0,fullName.indexOf(' ')));
                student.setLastName(fullName.substring(fullName.indexOf(' ') + 1));
                student.setMajor("undeclared");
                student.setPhoneNumber(phone.getText().toString());
                student.setSchool(schoolsSpinner.getSelectedItem().toString());
                student.setStreet(street.getText().toString());
                student.setZipcode(zipcode.getText().toString());
                mUsersDatabaseRef.setValue(student);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                AuthUI.getInstance().signOut(this);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}
