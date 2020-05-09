package com.example.covidanalyzerindia;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidanalyzerindia.util.CovidDataService;
import com.example.covidanalyzerindia.util.JavaScriptFunctionService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class OverallCasesActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_cases);

        try {
            new AsyncCovidDataApiTask().execute(CovidDataService.COVID_DATA_RESOURCE);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Sorry! Couldn't get data from the server.",Toast.LENGTH_LONG).show();
        }

//        TableLayout tableLayout = findViewById(R.id.caseCountTable);
//        TableRow tableRow = new TableRow(this);
//        tableRow.setLayoutParams(new TableRow.LayoutParams( LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//        tableRow.setBackgroundColor(Color.GRAY);
//
//        TextView textview = new TextView(this);
//        textview.setLayoutParams(new TableRow.LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//        textview.setText("Hello");
//        textview.setTextColor(Color.BLACK);
//        textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        tableRow.addView(textview);
//
//        tableLayout.addView(tableRow, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
    }

    private class AsyncCovidDataApiTask extends AsyncTask<String, String, CovidDataService> {

        private final String TAG = OverallCasesActivity.AsyncCovidDataApiTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected CovidDataService doInBackground(String... strings) {
            String responseJSONData;
            CovidDataService covidDataService = null;
            try {
                if (CovidDataService.getInstance() != null) {
                    return CovidDataService.getInstance();
                }
                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream in = new BufferedInputStream(conn.getInputStream());
                responseJSONData = convertStreamToString(in);
                JSONObject jsonObj = new JSONObject(responseJSONData);
                JSONArray caseTimeSeries = jsonObj.getJSONArray("cases_time_series");
                JSONArray stateWise = jsonObj.getJSONArray("statewise");
                JSONArray tested = jsonObj.getJSONArray("tested");
                Log.i(TAG, "caseTimeSeries Array : " + jsonObj.getJSONArray("cases_time_series"));
                covidDataService = new CovidDataService(caseTimeSeries, stateWise, tested);
                CovidDataService.setInstance(covidDataService);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                e.printStackTrace();
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            return covidDataService;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        protected void onPostExecute(CovidDataService covidDataService) {
            super.onPostExecute(covidDataService);
            TableLayout tableLayout = findViewById(R.id.caseCountTable);

            if (covidDataService != null) {
                try {
                    final List<String> stateCaseCount = covidDataService.getStateCaseCount();

                    for (int i = 0; i < stateCaseCount.size(); i++) {

                        String[] stateData = stateCaseCount.get(i).split(",");
                        TableRow tableRow = new TableRow(OverallCasesActivity.this);
                        tableRow.setLayoutParams(new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                        tableRow.setBackgroundColor(Color.WHITE);
                        tableRow.setPadding(5,5,5,5);

                        TextView textview1 = new TextView(OverallCasesActivity.this);
                        textview1.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1.0f));
                        textview1.setText(stateData[0]);
                        textview1.setTextColor(Color.BLACK);
                        textview1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        tableRow.addView(textview1);

                        TextView textview2 = new TextView(OverallCasesActivity.this);
                        textview2.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1.0f));
                        textview2.setText(stateData[1]);
                        textview2.setTextColor(Color.BLACK);
                        textview2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        tableRow.addView(textview2);

                        TextView textview3 = new TextView(OverallCasesActivity.this);
                        textview3.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1.0f));
                        textview3.setText(stateData[2]);
                        textview3.setTextColor(Color.BLACK);
                        textview3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        tableRow.addView(textview3);

                        TextView textview4 = new TextView(OverallCasesActivity.this);
                        textview4.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1.0f));
                        textview4.setText(stateData[3]);
                        textview4.setTextColor(Color.BLACK);
                        textview4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        tableRow.addView(textview4);

                        TextView textview5 = new TextView(OverallCasesActivity.this);
                        textview5.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1.0f));
                        textview5.setText(stateData[4]);
                        textview5.setTextColor(Color.BLACK);
                        textview5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        tableRow.addView(textview5);

                        tableLayout.addView(tableRow, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            }

        }

        private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return sb.toString();
        }

    }

}
