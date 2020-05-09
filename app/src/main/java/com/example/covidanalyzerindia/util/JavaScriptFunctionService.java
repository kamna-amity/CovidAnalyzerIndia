package com.example.covidanalyzerindia.util;

public class JavaScriptFunctionService {

    public String getJavaScriptFunctionCall(String graphType, String graphLabel, String graphBackground, String graphBorder, String graphX, String graphY, String aspectRatio){
        StringBuilder javaScriptFunctionCall = new StringBuilder();
        javaScriptFunctionCall.append("javascript:displayChart(")
                .append("'")
                .append(graphType)
                .append("','")
                .append(graphLabel)
                .append("','")
                .append(graphBackground)
                .append("','")
                .append(graphBorder)
                .append("','")
                .append(graphX)
                .append("','")
                .append(graphY)
                .append("','")
                .append(aspectRatio)
                .append("')");
        return javaScriptFunctionCall.toString();
    }

    public String getJavaScriptFunctionCall(String graphType, String graphLabel1, String graphLabel2, String graphLabel3, String graphLabel4,
                                            String graphBackground1, String graphBackground2, String graphBackground3, String graphBackground4,
                                            String graphBorder1, String graphBorder2, String graphBorder3, String graphBorder4,
                                            String graphX,
                                            String graphY1, String graphY2, String graphY3, String graphY4){
        StringBuilder javaScriptFunctionCall = new StringBuilder();
        javaScriptFunctionCall.append("javascript:displayChart(")
                .append("'")
                .append(graphType)
                .append("','")
                .append(graphLabel1)
                .append("','")
                .append(graphLabel2)
                .append("','")
                .append(graphLabel3)
                .append("','")
                .append(graphLabel4)
                .append("','")
                .append(graphBackground1)
                .append("','")
                .append(graphBackground2)
                .append("','")
                .append(graphBackground3)
                .append("','")
                .append(graphBackground4)
                .append("','")
                .append(graphBorder1)
                .append("','")
                .append(graphBorder2)
                .append("','")
                .append(graphBorder3)
                .append("','")
                .append(graphBorder4)
                .append("','")
                .append(graphX)
                .append("','")
                .append(graphY1)
                .append("','")
                .append(graphY2)
                .append("','")
                .append(graphY3)
                .append("','")
                .append(graphY4)
                .append("')");
        return javaScriptFunctionCall.toString();
    }

}
