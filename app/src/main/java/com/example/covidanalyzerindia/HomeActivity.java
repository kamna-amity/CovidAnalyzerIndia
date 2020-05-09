package com.example.covidanalyzerindia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.covidanalyzerindia.util.CovidDataService;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {


    private static final String TAG = HomeActivity.class.getSimpleName();
    CovidDataService covidDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {
            //covidDataService = CovidDataService.getInstance(true); // refresh data
            //Log.i(TAG,"Got Covid data from the server");
            Toast.makeText(getApplicationContext(),"Success! Got data from the server.",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Sorry! Couldn't get data from the server.",Toast.LENGTH_LONG).show();
        }
    }

    public void startIndiaStatisticsActivity(View view) {
        Intent intent = new Intent(this, IndiaStatisticsActivity.class);
        startActivity(intent);
    }

    public void startStateComparisonActivity(View view) {
        Intent intent = new Intent(this, StateComparisonActivity.class);
        startActivity(intent);
    }
}
