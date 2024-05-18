package br.com.fiap.trafficManagement.utilitiesFunctions;

import br.com.fiap.trafficManagement.model.TrafficLightColor;
import br.com.fiap.trafficManagement.model.WeatherCondition;

public class TrafficLightCalculator {

    public static int calculateBaseTime(TrafficLightColor color) {
        switch (color) {
            case GREEN:
                return 60; // segundos
            case YELLOW:
                return 10; // segundos
            case RED:
                return 40; // segundos
            default:
                return 0;
        }
    }

    // Ajuste de tempo baseado na densidade do tráfego
    public static int timerBasedOnTraffic(int trafficDensity) {
        return trafficDensity > 100 ? 15 : 0;
    }

    // Ajuste de tempo baseado na condição climática
    public static int timerBasedOnWeather(WeatherCondition weatherCondition) {
        switch (weatherCondition) {
            case RAINY:
                return 5;
            case FOGGY:
                return 10;
            case SNOWY:
                return 15;
            default:
                return 0;
        }
    }
}
