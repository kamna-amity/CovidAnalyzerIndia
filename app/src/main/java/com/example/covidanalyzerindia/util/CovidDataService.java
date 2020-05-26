package com.example.covidanalyzerindia.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CovidDataService implements Serializable {

    public static final String COVID_DATA_RESOURCE = "https://api.covid19india.org/data.json";
    private static final String TAG = "COVID Data Service";
    private static final String SEPARATOR = ",";
    private static final int DATA_START_POSITION = 40;
    private static final int STATE_DATA_START_POSITION = 1;

    private static CovidDataService instance = null;

    private JSONArray caseTimeSeries;
    private JSONArray stateWise;
    private JSONArray tested;

    public CovidDataService(JSONArray caseTimeSeries, JSONArray stateWise, JSONArray tested) {
        this.caseTimeSeries = caseTimeSeries;
        this.stateWise = stateWise;
        this.tested = tested;

    }

    public String getCaseTimeSeriesDatesString() throws JSONException {
        return convertToString(this.getCaseTimeSeriesDates().subList(DATA_START_POSITION,caseTimeSeries.length()));
    }

    // Daily Cases
    public String getCaseTimeSeriesDailyConfirmedCasesString() throws JSONException {
        return convertToString(this.getCaseTimeSeriesCasesOf("dailyconfirmed").subList(DATA_START_POSITION,caseTimeSeries.length()));
    }

    public String getCaseTimeSeriesDailyActiveCasesString() throws JSONException {
        return convertToString(this.getCaseTimeSeriesDailyActiveCases().subList(DATA_START_POSITION,caseTimeSeries.length()));
    }

    public String getCaseTimeSeriesDailyRecoveredCasesString() throws JSONException {
        return convertToString(this.getCaseTimeSeriesCasesOf("dailyrecovered").subList(DATA_START_POSITION,caseTimeSeries.length()));
    }

    public String getCaseTimeSeriesDailyDeceasedCasesString() throws JSONException {
        return convertToString(this.getCaseTimeSeriesCasesOf("dailydeceased").subList(DATA_START_POSITION,caseTimeSeries.length()));
    }


    // Total Cases
    public String getCaseTimeSeriesTotalConfirmedCasesString() throws JSONException {
        return convertToString(this.getCaseTimeSeriesCasesOf("totalconfirmed").subList(DATA_START_POSITION,caseTimeSeries.length()));
    }

    public String getCaseTimeSeriesTotalActiveCasesString() throws JSONException {
        return convertToString(this.getCaseTimeSeriesTotalActiveCases().subList(DATA_START_POSITION,caseTimeSeries.length()));
    }

    public String getCaseTimeSeriesTotalRecoveredCasesString() throws JSONException {
        return convertToString(this.getCaseTimeSeriesCasesOf("totalrecovered").subList(DATA_START_POSITION,caseTimeSeries.length()));
    }

    public String getCaseTimeSeriesTotalDeceasedCasesString() throws JSONException {
        return convertToString(this.getCaseTimeSeriesCasesOf("totaldeceased").subList(DATA_START_POSITION,caseTimeSeries.length()));
    }

    // State wise Cases
    public String getStatesString() throws JSONException {
        return convertToString(this.getStates().subList(STATE_DATA_START_POSITION,stateWise.length()));
    }
    public String getStateWiseCasesConfirmedCasesString() throws JSONException {
        return convertToString(this.getStateWiseCasesOf("confirmed").subList(STATE_DATA_START_POSITION,stateWise.length()));
    }
    public String getStateWiseCasesActiveCasesString() throws JSONException {
        return convertToString(this.getStateWiseCasesOf("active").subList(STATE_DATA_START_POSITION,stateWise.length()));
    }
    public String getStateWiseCasesRecoveredCasesString() throws JSONException {
        return convertToString(this.getStateWiseCasesOf("recovered").subList(STATE_DATA_START_POSITION,stateWise.length()));
    }
    public String getStateWiseCasesDeceasedCasesString() throws JSONException {
        return convertToString(this.getStateWiseCasesOf("deaths").subList(STATE_DATA_START_POSITION,stateWise.length()));
    }
    public String getStateWiseRecoveryRateString() throws JSONException {
        return convertToString(this.getStateWiseRateOf("recovered").subList(STATE_DATA_START_POSITION,stateWise.length()));
    }
    public String getStateWiseDeathRateString() throws JSONException {
        return convertToString(this.getStateWiseRateOf("deaths").subList(STATE_DATA_START_POSITION,stateWise.length()));
    }
    public String getStatesForContributingFactorsString() throws JSONException {
        return convertToString(this.getStatesForContributingFactors().subList(STATE_DATA_START_POSITION,stateWise.length()-3)); // -3 because we have removed 3 items
    }

    private List<String> getCaseTimeSeriesDates() throws JSONException {
        ArrayList<String> caseTimeSeriesDates = new ArrayList<>();
        for (int i=0; i<caseTimeSeries.length(); i++){
            JSONObject caseTimeSeriesJson = (JSONObject) caseTimeSeries.get(i);
            caseTimeSeriesDates.add(caseTimeSeriesJson.getString("date"));
        }
        return caseTimeSeriesDates;
    }

    private List<String> getCaseTimeSeriesDailyActiveCases() throws JSONException {
        ArrayList<String> caseTimeSeriesDailyActiveCases = new ArrayList<>();
        for (int i=0; i<caseTimeSeries.length(); i++){
            JSONObject caseTimeSeriesJson = (JSONObject) caseTimeSeries.get(i);
            int active_cases = Integer.parseInt(caseTimeSeriesJson.getString("dailyconfirmed")) - Integer.parseInt(caseTimeSeriesJson.getString("dailyrecovered"));
            caseTimeSeriesDailyActiveCases.add(Integer.toString(active_cases));
        }
        return caseTimeSeriesDailyActiveCases;
    }

    private List<String> getCaseTimeSeriesTotalActiveCases() throws JSONException {
        ArrayList<String> caseTimeSeriesTotalActiveCases = new ArrayList<>();
        for (int i=0; i<caseTimeSeries.length(); i++){
            JSONObject caseTimeSeriesJson = (JSONObject) caseTimeSeries.get(i);
            int active_cases = Integer.parseInt(caseTimeSeriesJson.getString("totalconfirmed")) - Integer.parseInt(caseTimeSeriesJson.getString("totalrecovered"));
            caseTimeSeriesTotalActiveCases.add(Integer.toString(active_cases));
        }
        return caseTimeSeriesTotalActiveCases;
    }

    private List<String> getCaseTimeSeriesCasesOf(String property) throws JSONException {
        ArrayList<String> caseTimeSeriesTotalCases = new ArrayList<>();
        for (int i=0; i<caseTimeSeries.length(); i++){
            JSONObject caseTimeSeriesJson = (JSONObject) caseTimeSeries.get(i);
            caseTimeSeriesTotalCases.add(caseTimeSeriesJson.getString(property));
        }
        return caseTimeSeriesTotalCases;
    }

    private List<String> getStates() throws JSONException {
        ArrayList<String> states = new ArrayList<>();
        for (int i=0; i<stateWise.length(); i++){
            JSONObject stateWiseJson = (JSONObject) stateWise.get(i);
            if (stateWiseJson.getString("state").equalsIgnoreCase("Andaman and Nicobar Islands")){
                states.add("Andaman Nicobar");
            }
            else if (stateWiseJson.getString("state").equalsIgnoreCase("Dadra and Nagar Haveli and Daman and Diu")){
                states.add("DN Haveli & DD");
            }
            else if (stateWiseJson.getString("state").equalsIgnoreCase("Jammu and Kashmir")){
                states.add("J & K");
            }
            else{
                states.add(stateWiseJson.getString("state"));
            }
        }
        return states;
    }

    private List<String> getStatesForContributingFactors() throws JSONException {
        List<String> states = getStates();
        states.remove("State Unassigned"); // not useful data coming from Covid API
        states.remove("Lakshadweep"); // Recovery rate was -1
        states.remove("Ladakh"); // There is no data for Ladakh as it got separated recently
        return states;
    }

    private List<String> getStateWiseCasesOf(String property) throws JSONException {
        ArrayList<String> stateWiseCases = new ArrayList<>();
        for (int i=0; i<stateWise.length(); i++){
            JSONObject stateWiseJson = (JSONObject) stateWise.get(i);
            stateWiseCases.add(stateWiseJson.getString(property));
        }
        return stateWiseCases;
    }

    private List<String> getStateWiseRateOf(String property) throws JSONException {
        ArrayList<String> stateWiseCases = new ArrayList<>();
        for (int i=0; i<stateWise.length(); i++){
            JSONObject stateWiseJson = (JSONObject) stateWise.get(i);
            float rate = 0;
            if (Integer.parseInt(stateWiseJson.getString("confirmed")) != 0){
                rate = ((float)Integer.parseInt(stateWiseJson.getString(property))) / Integer.parseInt(stateWiseJson.getString("confirmed"));
            }
            else{
                rate = -1; // If no confirmed cases, then recovery rate = -1
            }

            System.out.println("State : "+stateWiseJson.getString("state")+"  RATE : "+rate);
            stateWiseCases.add(String.valueOf(rate));
        }
        System.out.println("RATES : "+stateWiseCases);
        return stateWiseCases;
    }

    public List<String> getStateCaseCount() throws JSONException {
        ArrayList<String> stateCaseCount = new ArrayList<>();
        for (int i=0; i<stateWise.length(); i++){
            JSONObject stateWiseJson = (JSONObject) stateWise.get(i);
            String stateName = stateWiseJson.getString("state");
            String confirmedCases = stateWiseJson.getString("confirmed");
            String recoveredCases = stateWiseJson.getString("recovered");
            String activeCases = String.valueOf(Integer.parseInt(confirmedCases)-Integer.valueOf(recoveredCases));
            String deceasedCases = stateWiseJson.getString("deaths");

            if (stateName.equalsIgnoreCase("Andaman and Nicobar Islands")){
                stateName = "Andaman Nicobar";
            }
            else if (stateName.equalsIgnoreCase("Dadra and Nagar Haveli and Daman and Diu")){
                stateName = "DN Haveli & DD";
            }
            else if (stateName.equalsIgnoreCase("Jammu and Kashmir")){
                stateName = "J & K";
            }
            stateCaseCount.add(stateName+","+confirmedCases+","+activeCases+","+recoveredCases+","+deceasedCases);
        }
        return stateCaseCount;
    }

    private String convertToString(List<String> list){
        StringBuilder listStr = new StringBuilder();
        for(int i = 0; i < list.size()-1; i++){
            listStr.append(list.get(i));
            listStr.append(SEPARATOR);
        }
        listStr.append(list.get(list.size()-1));
        return listStr.toString();
    }


    public static CovidDataService getInstance() {
        return instance;
    }

    public static void setInstance(CovidDataService instance) {
        CovidDataService.instance = instance;
    }
}
