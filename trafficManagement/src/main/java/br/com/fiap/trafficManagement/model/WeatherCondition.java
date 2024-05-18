package br.com.fiap.trafficManagement.model;

public enum WeatherCondition {
    SUNNY("sunny"),
    USER("cloudy"),
    RAINY("rainy"),
    FOGGY("foggy"),
    WINDY("windy"),
    SNOWY("snowy");

    private String weatherCondition;

    WeatherCondition(String weatherCondition){
        this.weatherCondition = weatherCondition;
    }

    public String getWeatherCondition(){
        return weatherCondition;
    }
}
