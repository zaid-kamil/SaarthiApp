package com.example.sarthi;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Historique extends AppCompatActivity implements HistoriqueHomeAdapter.ItemClickListener {

    FirebaseAuth mAuth = null;
    RecyclerView mRecyclerView;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mReference,mReference1;
    ArrayList<Trip> mTripList = new ArrayList<>();
    HistoriqueHomeAdapter mTripAdapter;
    Trip trip;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historique_layout);

        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("History and Balance");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final String UserId = mAuth.getInstance().getCurrentUser().getUid();

        final ProgressBar myProgress2 = findViewById(R.id.progressBarHome);
        trip = new Trip();
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReference = mDatabase.getReference("TripsArchivé");
        mReference1 = mDatabase.getReference("Passenger");

        mTripAdapter = new HistoriqueHomeAdapter(mTripList);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mTripList.clear();
                myProgress2.setVisibility(View.VISIBLE);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    trip = ds.getValue(Trip.class);
                    if (trip.getIdChauffeur().equals(UserId)){
                            myProgress2.setVisibility(View.INVISIBLE);
                            mTripList.add(trip);
                        }
                }
                mRecyclerView.setAdapter(mTripAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        myProgress2.setVisibility(View.INVISIBLE);
        mTripAdapter.setClickListener(this);



    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this,"You clicked " + mTripAdapter.getItem(position) + " on row number " + position,Toast.LENGTH_LONG).show();
    }
}
