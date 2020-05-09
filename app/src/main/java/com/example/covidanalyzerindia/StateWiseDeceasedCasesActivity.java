package com.example.covidanalyzerindia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

import static com.example.covidanalyzerindia.util.ApplicationConstants.DECEASED_COLOR;

public class StateWiseDeceasedCasesActivity extends AppCompatActivity {

    private static final String LOCAL_RESOURCE = "file:///android_asset/singleLineGraph.html";
    private static final String GRAPH_TYPE = "horizontalBar";
    private static final String GRAPH_LABEL = "Number of Deceased Cases";
    private static final String GRAPH_BACKGROUND_COLOR = DECEASED_COLOR;
    private static final String GRAPH_BORDER_COLOR = DECEASED_COLOR;
    private static final String GRAPH_ASPECT_RATIO = "0.6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_wise_deceased_cases);

        try {
            new StateWiseDeceasedCasesActivity.AsyncCovidDataApiTask().execute(CovidDataService.COVID_DATA_RESOURCE);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Sorry! Couldn't get data from the server.",Toast.LENGTH_LONG).show();
        }
    }

    private class AsyncCovidDataApiTask extends AsyncTask<String, String, CovidDataService> {

        private final String TAG = StateWiseDeceasedCasesActivity.AsyncCovidDataApiTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected CovidDataService doInBackground(String... strings) {
            String responseJSONData;
            CovidDataService covidDataService=null;
            try {
                if (CovidDataService.getInstance() != null){
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

        @Override
        protected void onPostExecute(CovidDataService covidDataService) {
            super.onPostExecute(covidDataService);

            if (covidDataService != null) {
                try {
                    final String graphX = covidDataService.getStatesString();
                    final String graphY = covidDataService.getStateWiseCasesDeceasedCasesString();

                    WebView myWebView = findViewById(R.id.stateWiseDeceasedCasesWebView);
                    myWebView.getSettings().setJavaScriptEnabled(true);
                    myWebView.loadUrl(LOCAL_RESOURCE);
                    myWebView.setWebViewClient(new WebViewClient() {
                        public void onPageFinished(WebView view, String url) {
                            JavaScriptFunctionService javaScriptFunctionService = new JavaScriptFunctionService();
                            String javaScriptFunctionCall = javaScriptFunctionService.getJavaScriptFunctionCall(GRAPH_TYPE,GRAPH_LABEL,GRAPH_BACKGROUND_COLOR,GRAPH_BORDER_COLOR,graphX,graphY,GRAPH_ASPECT_RATIO);
                            view.loadUrl(javaScriptFunctionCall);
                        }
                    });
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
}
