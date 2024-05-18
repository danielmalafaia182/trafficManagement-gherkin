package br.com.fiap.trafficManagement.model;

public enum TrafficLightColor {
    GREEN("green"),
    YELLOW("yellow"),
    RED("red");

    private String trafficLightColor;

    TrafficLightColor(String trafficLightColor){
        this.trafficLightColor = trafficLightColor;
    }

    public String getTrafficLightColor(){
        return trafficLightColor;
    }
}
