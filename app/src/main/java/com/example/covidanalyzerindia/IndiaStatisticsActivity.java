package com.example.covidanalyzerindia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IndiaStatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_india_statistics);
    }

    public void startOverallCasesActivity(View view){
        Intent intent = new Intent(this, OverallCasesActivity.class);
        startActivity(intent);
    }

    public void startAllInOneCasesActivity(View view) {
        Intent intent = new Intent(this, AllInOneActivity.class);
        startActivity(intent);
    }

    public void startConfirmedCasesActivity(View view) {
        Intent intent = new Intent(this, ConfirmedCasesActivity.class);
        startActivity(intent);
    }

    public void startActiveCasesActivity(View view) {
        Intent intent = new Intent(this, ActiveCasesActivity.class);
        startActivity(intent);
    }

    public void startRecoveredCasesActivity(View view) {
        Intent intent = new Intent(this, RecoveredCasesActivity.class);
        startActivity(intent);
    }

    public void startDeceasedCasesActivity(View view) {
        Intent intent = new Intent(this, DeceasedCasesActivity.class);
        startActivity(intent);
    }

}
