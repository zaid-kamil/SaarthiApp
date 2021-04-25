package com.example.sarthi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MyDemands extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DemandAdapter.ItemClickListener {

    FirebaseAuth mAuth = null;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    ArrayList<DemandModel> mTripList = new ArrayList<>();
    DemandAdapter mTripAdapter;
    DemandModel trip;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydemands_layout);

        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("My Demands");


        DrawerLayout mDrawer = findViewById(R.id.mDrawer);

        ActionBarDrawerToggle actionToggle = new ActionBarDrawerToggle(this, mDrawer, myToolbar, R.string.drawer_open, R.string.drawer_close);

        mDrawer.addDrawerListener(actionToggle);
        actionToggle.syncState();

        NavigationView mNavigation = findViewById(R.id.mNavig);
        mNavigation.setNavigationItemSelectedListener(this);

        final String UserId = mAuth.getInstance().getCurrentUser().getUid();
        Menu myMenu = mNavigation.getMenu();
        MenuItem allUsers = myMenu.findItem(R.id.Users);
        if (UserId.equals("OSJvcLc0eJfpYLWjsJ2TU1ybubT2")){
            allUsers.setVisible(true);
        }

        final ProgressBar myProgress2 = findViewById(R.id.progressBarHome);
        trip = new DemandModel();
        mRecyclerView = findViewById(R.id.recyclerview);
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Trips");


        mTripAdapter = new DemandAdapter(mTripList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myProgress2.setVisibility(View.VISIBLE);

        mReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mTripList.clear();
                myProgress2.setVisibility(View.VISIBLE);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    myProgress2.setVisibility(View.INVISIBLE);
                    trip = ds.getValue(DemandModel.class);

                    if (trip.getIdDemandeur().equals(UserId)){
                        mTripList.add(trip); }
                }
                mRecyclerView.setAdapter(mTripAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        myProgress2.setVisibility(View.INVISIBLE);
        mTripAdapter.setClickListener(this);




        loadNavigationInfo();

    }

    private void loadNavigationInfo() {

        String UserId = mAuth.getInstance().getCurrentUser().getUid();
        final StorageReference profileImageRef = storageReference
                .child(UserId+".jpg");
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("users");
        final DatabaseReference userRef = mReference.child(UserId);

        NavigationView navigationView = findViewById(R.id.mNavig);
        View navHeader = navigationView.getHeaderView(0);
        final ImageView yImage = navHeader.findViewById(R.id.profile_image_navig);
        final TextView userName = navHeader.findViewById(R.id.UserNameHeaderNav);
        final TextView email = navHeader.findViewById(R.id.emailHeaderNav);


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user muUser = dataSnapshot.getValue(user.class);
                userName.setText(muUser.getName());
                email.setText(muUser.getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        profileImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri.toString()).into(yImage);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch(id){
            case R.id.My_Profile:
                Intent Navig = new Intent(MyDemands.this, Profile.class);
                startActivity(Navig);
                break;

            case R.id.Chat:
                Intent Navig1 = new Intent(MyDemands.this, Messages.class);
                startActivity(Navig1);
                break;

            case R.id.Offers:
                Intent Navig2 = new Intent(MyDemands.this, Home.class);
                startActivity(Navig2);
                break;

            case R.id.demands:
                Intent Navig3 = new Intent(MyDemands.this, Demands.class);
                startActivity(Navig3);
                break;

            case R.id.MyOffers:
                Intent Navig4 = new Intent(MyDemands.this, MyOffers.class);
                startActivity(Navig4);
                break;

            /*case R.id.MyDemands:
                Intent Navig5 = new Intent(MyDemands.this, MyDemands.class);
                startActivity(Navig5);
                break;*/

            case R.id.Users:
                Intent Navig6 = new Intent(MyDemands.this, Signaler.class);
                startActivity(Navig6);
                break;
        }


        closeDrawer();
        return true;
    }

    private void closeDrawer() {
        DrawerLayout mDrawer = findViewById(R.id.mDrawer);
        mDrawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout mDrawer = findViewById(R.id.mDrawer);
        if (mDrawer.isDrawerOpen(GravityCompat.START))
            closeDrawer();
        else
            super.onBackPressed();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this,"You clicked " + mTripAdapter.getItem(position) + " on row number " + position,Toast.LENGTH_LONG).show();
    }
}
