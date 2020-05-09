package com.example.covidanalyzerindia.util;

public class StateInformation {

    private String state;
    private String stateCode;
    private String stateNotes;
    private int confirmed;
    private int active;
    private int recovered;
    private int deaths;
    private int deltaConfirmed;
    private int deltaRecovered;
    private int deltaDeaths;
    private String lastUpdatedTime;

    public StateInformation(String state, String stateCode, String stateNotes, int confirmed, int active, int recovered, int deaths, int deltaConfirmed, int deltaRecovered, int deltaDeaths, String lastUpdatedTime) {
        this.state = state;
        this.stateCode = stateCode;
        this.stateNotes = stateNotes;
        this.confirmed = confirmed;
        this.active = active;
        this.recovered = recovered;
        this.deaths = deaths;
        this.deltaConfirmed = deltaConfirmed;
        this.deltaRecovered = deltaRecovered;
        this.deltaDeaths = deltaDeaths;
        this.lastUpdatedTime = lastUpdatedTime;
    }

    //    {
//        "active": "27661",
//            "confirmed": "39884",
//            "deaths": "1325",
//            "deltaconfirmed": "55",
//            "deltadeaths": "2",
//            "deltarecovered": "42",
//            "lastupdatedtime": "03/05/2020 12:12:45",
//            "recovered": "10894",
//            "state": "Total",
//            "statecode": "TT",
//            "statenotes": ""
//    }

}
