package com.example.covidanalyzerindia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StateComparisonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_comparison);
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

    public void startConfirmedCasesActivity(View view) {
        Intent intent = new Intent(this, StateWiseConfirmedCasesActivity.class);
        startActivity(intent);
    }

    public void startActiveCasesActivity(View view) {
        Intent intent = new Intent(this, StateWiseActiveCasesActivity.class);
        startActivity(intent);
    }

    public void startRecoveredCasesActivity(View view) {
        Intent intent = new Intent(this, StateWiseRecoveredCasesActivity.class);
        startActivity(intent);
    }

    public void startDeceasedCasesActivity(View view) {
        Intent intent = new Intent(this, StateWiseDeceasedCasesActivity.class);
        startActivity(intent);
    }

    public void startRecoveryRateActivity(View view) {
        Intent intent = new Intent(this, StateWiseRecoveryRateActivity.class);
        startActivity(intent);
    }

    public void startDeathRateActivity(View view) {
        Intent intent = new Intent(this, StateWiseDeathRateActivity.class);
        startActivity(intent);
    }
}
