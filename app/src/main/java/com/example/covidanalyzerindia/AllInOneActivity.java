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

import static com.example.covidanalyzerindia.util.ApplicationConstants.ACTIVE_COLOR;
import static com.example.covidanalyzerindia.util.ApplicationConstants.CONFIRMED_COLOR;
import static com.example.covidanalyzerindia.util.ApplicationConstants.DECEASED_COLOR;
import static com.example.covidanalyzerindia.util.ApplicationConstants.RECOVERED_COLOR;

public class AllInOneActivity extends AppCompatActivity {

    private static final String LOCAL_RESOURCE_FOUR = "file:///android_asset/fourLineGraph.html";
    private static final String GRAPH_TYPE = "line";
    private static final String GRAPH_LABEL_CONFIRMED = "Confirmed";
    private static final String GRAPH_LABEL_ACTIVE = "Active";
    private static final String GRAPH_LABEL_RECOVERED = "Recovered";
    private static final String GRAPH_LABEL_DECEASED = "Deceased";
    private static final String GRAPH_BACKGROUND_COLOR = "rgb(102,187,106)";
    private static final String GRAPH_BORDER_COLOR_CONFIRMED = CONFIRMED_COLOR;
    private static final String GRAPH_BORDER_COLOR_ACTIVE = ACTIVE_COLOR;
    private static final String GRAPH_BORDER_COLOR_RECOVERED = RECOVERED_COLOR;
    private static final String GRAPH_BORDER_COLOR_DECEASED = DECEASED_COLOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_in_one);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        try {
            new AllInOneActivity.AsyncCovidDataApiTask().execute(CovidDataService.COVID_DATA_RESOURCE);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Sorry! Couldn't get data from the server.",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private class AsyncCovidDataApiTask extends AsyncTask<String, String, CovidDataService> {

        private final String TAG = AllInOneActivity.AsyncCovidDataApiTask.class.getSimpleName();

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
                    final String graphX = covidDataService.getCaseTimeSeriesDatesString();
                    final String graphY1 = covidDataService.getCaseTimeSeriesDailyConfirmedCasesString();
                    final String graphY2 = covidDataService.getCaseTimeSeriesDailyActiveCasesString();
                    final String graphY3 = covidDataService.getCaseTimeSeriesDailyRecoveredCasesString();
                    final String graphY4 = covidDataService.getCaseTimeSeriesDailyDeceasedCasesString();

                    WebView myWebView = findViewById(R.id.allInOneCasesWebView);
                    myWebView.getSettings().setJavaScriptEnabled(true);
                    myWebView.loadUrl(LOCAL_RESOURCE_FOUR);
                    myWebView.setWebViewClient(new WebViewClient() {
                        public void onPageFinished(WebView view, String url) {
                            JavaScriptFunctionService javaScriptFunctionService = new JavaScriptFunctionService();
                            String javaScriptFunctionCall = javaScriptFunctionService.getJavaScriptFunctionCall(GRAPH_TYPE,
                                    GRAPH_LABEL_CONFIRMED, GRAPH_LABEL_ACTIVE, GRAPH_LABEL_RECOVERED, GRAPH_LABEL_DECEASED,
                                    GRAPH_BORDER_COLOR_CONFIRMED, GRAPH_BORDER_COLOR_ACTIVE, GRAPH_BORDER_COLOR_RECOVERED, GRAPH_BORDER_COLOR_DECEASED,
                                    GRAPH_BORDER_COLOR_CONFIRMED,GRAPH_BORDER_COLOR_ACTIVE,GRAPH_BORDER_COLOR_RECOVERED,GRAPH_BORDER_COLOR_DECEASED,
                                    graphX,
                                    graphY1,graphY2,graphY3,graphY4);
                            view.loadUrl(javaScriptFunctionCall);
                        }
                    });


                    final String graphY1Total = covidDataService.getCaseTimeSeriesTotalConfirmedCasesString();
                    final String graphY2Total = covidDataService.getCaseTimeSeriesTotalActiveCasesString();
                    final String graphY3Total = covidDataService.getCaseTimeSeriesTotalRecoveredCasesString();
                    final String graphY4Total = covidDataService.getCaseTimeSeriesTotalDeceasedCasesString();

                    WebView myWebViewTotal = findViewById(R.id.allInOneTotalCasesWebView);
                    myWebViewTotal.getSettings().setJavaScriptEnabled(true);
                    myWebViewTotal.loadUrl(LOCAL_RESOURCE_FOUR);
                    myWebViewTotal.setWebViewClient(new WebViewClient() {
                        public void onPageFinished(WebView view, String url) {
                            JavaScriptFunctionService javaScriptFunctionService = new JavaScriptFunctionService();
                            String javaScriptFunctionCall = javaScriptFunctionService.getJavaScriptFunctionCall(GRAPH_TYPE,
                                    GRAPH_LABEL_CONFIRMED, GRAPH_LABEL_ACTIVE, GRAPH_LABEL_RECOVERED, GRAPH_LABEL_DECEASED,
                                    GRAPH_BORDER_COLOR_CONFIRMED, GRAPH_BORDER_COLOR_ACTIVE, GRAPH_BORDER_COLOR_RECOVERED, GRAPH_BORDER_COLOR_DECEASED,
                                    GRAPH_BORDER_COLOR_CONFIRMED,GRAPH_BORDER_COLOR_ACTIVE,GRAPH_BORDER_COLOR_RECOVERED,GRAPH_BORDER_COLOR_DECEASED,
                                    graphX,
                                    graphY1Total,graphY2Total,graphY3Total,graphY4Total);
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
