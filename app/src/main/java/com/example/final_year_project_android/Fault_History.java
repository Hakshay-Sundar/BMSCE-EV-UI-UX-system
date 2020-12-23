package com.example.final_year_project_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Fault_History extends AppCompatActivity {


    RecyclerView faultRecycler;

    DatabaseReference db;
    FirebaseRecyclerOptions<faultModel> options;
    FirebaseRecyclerAdapter<faultModel,faultViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_fault__history);

        faultRecycler = (RecyclerView)findViewById(R.id.faultHistoryRecycler);

        db = FirebaseDatabase.getInstance().getReference().child("fault");
        options = new FirebaseRecyclerOptions.Builder<faultModel>().setQuery(db,faultModel.class).build();

        adapter = new FirebaseRecyclerAdapter<faultModel, faultViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull faultViewHolder holder, int position, @NonNull faultModel model) {
                holder.faultTitle.setText(model.getTime());
                holder.faultInput1.setText(model.getCurrent());
                holder.faultInput2.setText(model.getTemperature());
                holder.faultInput3.setText(model.getVoltage());
            }

            @NonNull
            @Override
            public faultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fault_history_cards,parent,false);
                return new faultViewHolder(view);
            }
        };

        faultRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter.startListening();
        faultRecycler.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null) adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null) adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null) adapter.startListening();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSwipeRight(Fault_History.this); //fire the slide left animation
    }

}