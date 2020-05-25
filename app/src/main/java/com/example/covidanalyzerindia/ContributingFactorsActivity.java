package com.example.covidanalyzerindia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ContributingFactorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributing_factors);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void startPollutionActivity(View view) {
        Intent intent = new Intent(this, ContributingFactorsPollutionActivity.class);
        startActivity(intent);
    }

    public void startPopulationActivity(View view) {
        Intent intent = new Intent(this, ContributingFactorsPopulationActivity.class);
        startActivity(intent);
    }

    public void startLiteracyRateActivity(View view) {
        Intent intent = new Intent(this, ContributingFactorsLiteracyRateActivity.class);
        startActivity(intent);
    }
}
