package com.tareksaidee.cunysecond.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tareksaidee.cunysecond.R;


public class MainLobby extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String ANONYMOUS = "anonymous";
    private static final int RC_SIGN_IN = 123;


    RecyclerView mRecyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRoomsDatabaseReference;
    private ChildEventListener mRoomsEventListener;
    private RoomsAdapter mRoomsAdapter;
    private String mUsername;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mDrawerOptions;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;
    private RoundedImageView userPicView;
    private TextView usernameTextView;
    private TextView userEmailTextView;
    private View navHeaderView;
    private ImageView background;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lobby);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        background = (ImageView) findViewById(R.id.mainactivity_bg);
//        InputStream stream = this.getResources().openRawResource(R.raw.bclogo);
//        background.setImageBitmap(BitmapFactory.decodeStream(stream));
        mUsername = ANONYMOUS;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRoomsDatabaseReference = mFirebaseDatabase.getReference().child("rooms");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.navigation_drawer_open,  /* "open drawer" description */
                R.string.navigation_drawer_close  /* "close drawer" description */
        );
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeaderView = navigationView.getHeaderView(0);
        userPicView = (RoundedImageView) navHeaderView.findViewById(R.id.imageView_header);
        usernameTextView = (TextView) navHeaderView.findViewById(R.id.username_header);
        userEmailTextView = (TextView) navHeaderView.findViewById(R.id.user_email_header);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_rooms);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRoomsAdapter = new RoomsAdapter(this);
        mRecyclerView.setAdapter(mRoomsAdapter);
        initilizeSignIn(FirebaseAuth.getInstance().getCurrentUser());
    }


    void initilizeSignIn(FirebaseUser user) {
        mUsername = user.getDisplayName();
        mRoomsAdapter.setUserName(mUsername);
        attachDatabaseReadListener();
        if (user.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(userPicView);
        }
        usernameTextView.setText(mUsername);
        userEmailTextView.setText(user.getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_room:
                Intent intent = new Intent(this, CreateRoom.class);
                intent.putExtra("rooms", mRoomsAdapter.getRooms());
                startActivity(intent);
                return true;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void attachDatabaseReadListener() {
        if (mRoomsEventListener == null) {
            mRoomsEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mRoomsAdapter.addRoom(dataSnapshot.getValue(Room.class));
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mRoomsDatabaseReference.addChildEventListener(mRoomsEventListener);
        }
    }

    void detachReadListener() {
        if (mRoomsEventListener != null) {
            mRoomsDatabaseReference.removeEventListener(mRoomsEventListener);
            mRoomsEventListener = null;
        }
    }

    void cleanUpOnSignout() {
        mUsername = ANONYMOUS;
        mRoomsAdapter.clear();
        mRoomsAdapter.setUserName(null);
        detachReadListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cleanUpOnSignout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeSignIn(FirebaseAuth.getInstance().getCurrentUser());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                //signed in
            } else {
                if (resultCode == RESULT_CANCELED) {
                    finish();
                }
            }

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

//TODO sort rooms by whatever
//TODO my rooms
//TODO send documents and audio
//TODO long click to save
//TODO number of participants per room
//TODO if a chat room is empty, loading symbol doesn't go away
