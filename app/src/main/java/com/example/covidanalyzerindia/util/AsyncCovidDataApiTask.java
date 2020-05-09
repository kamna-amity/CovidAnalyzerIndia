package com.example.covidanalyzerindia.util;

import android.os.AsyncTask;
import android.util.Log;

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

public class AsyncCovidDataApiTask extends AsyncTask<String, String, String> {

    private static final String TAG = AsyncCovidDataApiTask.class.getSimpleName();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = null;
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
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
        return response;
    }

    @Override
    protected void onPostExecute(String responseJSONData) {
        super.onPostExecute(responseJSONData);

        if (responseJSONData != null) {
            try {
                JSONObject jsonObj = new JSONObject(responseJSONData);
                JSONArray caseTimeSeries = jsonObj.getJSONArray("cases_time_series");
                JSONArray stateWise = jsonObj.getJSONArray("statewise");
                JSONArray tested = jsonObj.getJSONArray("tested");
                Log.i(TAG,"caseTimeSeries Array : "+jsonObj.getJSONArray("cases_time_series"));
                //Log.i(TAG,"caseTimeSeries Object : "+jsonObj.getJSONObject("cases_time_series"));

                CovidDataService covidDataService = new CovidDataService(caseTimeSeries, stateWise, tested);
                //CovidDataService.setInstance(covidDataService);
            }
            catch (JSONException e){
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
