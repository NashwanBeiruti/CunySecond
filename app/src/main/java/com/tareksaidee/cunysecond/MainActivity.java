package com.tareksaidee.cunysecond;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.VideoView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tareksaidee.cunysecond.chat.MainLobby;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private static final int RC_SIGN_IN = 123;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private List<AuthUI.IdpConfig> providers;
    VideoView videoview;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsersReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoview = (VideoView) findViewById(R.id.background_video);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.nyc3);
        videoview.setVideoURI(uri);
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        videoview.start();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUsersReference = mFirebaseDatabase.getReference().child("users");
        mFirebaseAuth = FirebaseAuth.getInstance();
        providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //initilizeSignIn(user);
                    checkUser();
                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);
                    //cleanUpOnSignout();
                }
            }
        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                //signed in
                mFirebaseAuth.addAuthStateListener(mAuthStateListener);
            } else {
                if (resultCode == RESULT_CANCELED) {
                    finish();
                }
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        videoview.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null){
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    public void buttonControl(View view){

        switch (view.getId()){
            case R.id.enroll_button:
                Intent myIntent = new Intent(MainActivity.this, Search.class);
                startActivity(myIntent);
                break;
            case R.id.account_button:
                Intent accountIntent = new Intent(MainActivity.this, UserAccount.class);
                startActivity(accountIntent);
                break;
            case R.id.calendar_button:
                Intent calendarIntent = new Intent(MainActivity.this, Calendar.class);
                startActivity(calendarIntent);
                break;
            case R.id.chat_button:
                Intent chatIntent = new Intent(MainActivity.this, MainLobby.class);
                startActivity(chatIntent);
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                AuthUI.getInstance().signOut(this);
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

    private void checkUser(){
        mUsersReference.orderByKey().equalTo(mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    Intent intent = new Intent(MainActivity.this,Registration.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
