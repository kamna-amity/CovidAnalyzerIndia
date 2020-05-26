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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void startIndiaStatisticsActivity(View view) {
        Intent intent = new Intent(this, IndiaStatisticsActivity.class);
        startActivity(intent);
    }

    public void startStateComparisonActivity(View view) {
        Intent intent = new Intent(this, StateComparisonActivity.class);
        startActivity(intent);
    }

    public void startContributingFactorsActivity(View view) {
        Intent intent = new Intent(this, ContributingFactorsActivity.class);
        startActivity(intent);
    }
}
