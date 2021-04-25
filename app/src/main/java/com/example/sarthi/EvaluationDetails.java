package com.example.sarthi;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EvaluationDetails extends AppCompatActivity implements EvaluationAdapter.ItemClickListener {

    DatabaseReference mReference;
    ArrayList<Evaluation> mEvaluationList = new ArrayList<>();
    EvaluationAdapter mEvaluationAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluation_details);

        final String idChauffeur = getIntent().getStringExtra("idProfile");

        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Ratings");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.recyclerview);
        mEvaluationAdapter = new EvaluationAdapter(mEvaluationList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mReference = FirebaseDatabase.getInstance().getReference().child("Evaluation").child(idChauffeur);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mEvaluationList.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Evaluation newEval = snapshot.getValue(Evaluation.class);
                    mEvaluationList.add(newEval);
                }

                mRecyclerView.setAdapter(mEvaluationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mEvaluationAdapter.setClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this,"You clicked " + mEvaluationAdapter.getItem(position) + " on row number " + position,Toast.LENGTH_LONG).show();
    }
}
