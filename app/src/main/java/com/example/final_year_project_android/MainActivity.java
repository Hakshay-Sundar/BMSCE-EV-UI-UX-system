package com.example.final_year_project_android;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.physicaloid.lib.Physicaloid;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Hello";
    String x = "",connect_check = "";
    Integer real_speed_DB = 0, batterySOC = 0, estimdist = 0, disttrav = 0;
    boolean stasto = false;
    Physicaloid mPhysicaloid = new Physicaloid(this);

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        TextView speed_text = (TextView) findViewById(R.id.speed_text);
        TextView battery_Soc = (TextView) findViewById(R.id.battery_percentage_text);
        TextView estimated_dist = (TextView) findViewById(R.id.estimated_distance_text);
        TextView dist_travelled = (TextView) findViewById(R.id.travelled_distance_text);

        ImageButton imageButtonConnect = (ImageButton) findViewById(R.id.image_button_connect);
        ImageButton imageButtonStartStop = (ImageButton) findViewById(R.id.image_button_start_stop);

        //Fault History View Initialisation
        MaterialCardView faultHistory = (MaterialCardView) findViewById(R.id.faultHistoryButton);

        //speedometer's gauge
        CustomGauge Speedo = (CustomGauge) findViewById(R.id.speed);

        //battery percentage's progress bar
        LinearProgressIndicator batteryPercentageBar = (LinearProgressIndicator) findViewById(R.id.battery_percentage_display);

        //FireBase - Write
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Setting up the reference for speedometer
        DatabaseReference Speedometer = database.getReference("Speed");
        Speedometer.setValue("0");
        //Setting up the reference for the battery percentage
        DatabaseReference BatteryPercentage = database.getReference("SOC");
        //Setting up the reference for the battery percentage
        DatabaseReference EstimatedDistance = database.getReference("Estimated Distance");
        //Setting up the reference for the battery percentage
        DatabaseReference TravelledDistance = database.getReference("Distance Travelled");
        TravelledDistance.setValue("0");

        // Write a message to the database complete

        //FireBase - Read
        //Reading data from DB for the Speedometer
        Speedometer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                //To get the integer value of speed
                real_speed_DB = (int) Double.parseDouble(value);
                //to set the speedometer's text
                speed_text.setText(value);
                //to set the speedometer's progress bar
                Speedo.setValue(real_speed_DB);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        //Reading data from the Db for the battery percentage or SOC
        BatteryPercentage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                //To get the integer value of battery percentage
                batterySOC = (int) Double.parseDouble(value);
                //to set the speedometer's text
                battery_Soc.setText(value);
                //to set the battery percentage progress bar
                batteryPercentageBar.setProgress(batterySOC);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        //Reading data from the Db for the Estimated Distance
        EstimatedDistance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                //To get the integer value of estimated distance
                estimdist = (int) Double.parseDouble(value);
                //to set the speedometer's text
                estimated_dist.setText(value);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        //Reading data from the Db for the Distance Travelled
        TravelledDistance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                //To get the integer value of travelled distance
                disttrav = (int) Double.parseDouble(value);
                //to set the speedometer's text
                dist_travelled.setText(value);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // Read from the database Complete

        //Connect Button Action
        imageButtonConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mPhysicaloid.setBaudrate(9600);
                if (mPhysicaloid.open()) {
                    Toast.makeText(MainActivity.this, "Connection Established", Toast.LENGTH_SHORT).show();
                    connect_check = "1";
                    imageButtonConnect.setEnabled(false);
                    imageButtonConnect.setAlpha(0.5f);

                } else {
                    //Error while connecting
                    Toast.makeText(MainActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //start-stop-button
        imageButtonStartStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (connect_check.equals("1")) {

                //start
                if (!stasto) {
                    x = "1";
                    Toast.makeText(MainActivity.this, "Start Signal Sent", Toast.LENGTH_SHORT).show();
                    stasto = true;
                    byte[] buf = x.getBytes();
                    mPhysicaloid.write(buf);
                }

                //stop
                else {
                    x = "2";
                    Toast.makeText(MainActivity.this, "Stop Signal Sent", Toast.LENGTH_SHORT).show();
                    stasto = false;
                    byte[] buf = x.getBytes();
                    mPhysicaloid.write(buf);
                }
              }
            }
        });

        //Fault History Button clicked
        faultHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this,Fault_History.class));
                Animatoo.animateSwipeLeft(MainActivity.this);
            }
        });

    }

}
