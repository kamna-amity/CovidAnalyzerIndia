package com.example.covidanalyzerindia.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StateInformationService {

    Context context;

    private static HashMap<String,String> POLLUTION = new HashMap<String,String>();
    private static HashMap<String,String> POPULATION = new HashMap<String,String>();
    private static HashMap<String,String> LITERACY_RATE = new HashMap<String,String>();

    public StateInformationService(Context context) {
        this.context = context;
    }

    public String getPollutionDataString(String stateList) {
        String[] states = stateList.split(",");
        HashMap<String,String> pollutionData = getPollutionData();
        List<String> pollutionDataList= new ArrayList<String>();
        for(int i=0;i<states.length;i++){
            if (pollutionData.containsKey(states[i])){
                pollutionDataList.add(pollutionData.get(states[i]));
            }
            else{
                System.out.println("======== State not found : "+states[i]);
                pollutionDataList.add("0");
            }
        }
        return convertToString(pollutionDataList);
    }

    public String getPopulationDataString(String stateList) {
        String[] states = stateList.split(",");
        HashMap<String,String> data = getPopulationData();
        List<String> dataList= new ArrayList<String>();
        for(int i=0;i<states.length;i++){
            if (data.containsKey(states[i])){
                dataList.add(data.get(states[i]));
            }
            else{
                System.out.println("======== State not found : "+states[i]);
                dataList.add("0");
            }
        }
        return convertToString(dataList);
    }

    public String getLiteracyRateDataString(String stateList) {
        String[] states = stateList.split(",");
        HashMap<String,String> data = getLiteracyRateData();
        List<String> dataList= new ArrayList<String>();
        for(int i=0;i<states.length;i++){
            if (data.containsKey(states[i])){
                dataList.add(data.get(states[i]));
            }
            else{
                System.out.println("======== State not found : "+states[i]);
                dataList.add("0");
            }
        }
        return convertToString(dataList);
    }

    public HashMap<String,String> getPollutionData() {
        if (POLLUTION.size() != 0){ // reads only once. If data is already there, just return
            return POLLUTION;
        }
        readAndFillData();
        return POLLUTION;
    }

    public HashMap<String,String> getPopulationData() {
        if (POPULATION.size() != 0){ // reads only once. If data is already there, just return
            return POPULATION;
        }
        readAndFillData();
        return POPULATION;
    }

    public HashMap<String,String> getLiteracyRateData()  {
        if (LITERACY_RATE.size() != 0){ // reads only once. If data is already there, just return
            return LITERACY_RATE;
        }
        readAndFillData();
        return LITERACY_RATE;
    }

    private void readAndFillData(){
        String line="";
        try{
            InputStream is = context.getAssets().open("stateInformation.csv");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] stateData = line.split(",");
                POLLUTION.put(stateData[0],stateData[2]);
                POPULATION.put(stateData[0],stateData[1]);
                LITERACY_RATE.put(stateData[0],stateData[3]);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    private String convertToString(List<String> list){
        StringBuilder listStr = new StringBuilder();
        for(int i = 0; i < list.size()-1; i++){
            listStr.append(list.get(i));
            listStr.append(",");
        }
        listStr.append(list.get(list.size()-1));
        return listStr.toString();
    }

}
