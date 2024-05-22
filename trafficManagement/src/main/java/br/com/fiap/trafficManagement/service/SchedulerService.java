package br.com.fiap.trafficManagement.service;

import br.com.fiap.trafficManagement.model.TrafficLight;
import br.com.fiap.trafficManagement.model.TrafficLightColor;
import br.com.fiap.trafficManagement.model.WeatherCondition;
import br.com.fiap.trafficManagement.repository.TrafficLightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class SchedulerService {

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    private TrafficLightRepository trafficLightRepository;

    public void scheduleTrafficLightColorChange(TrafficLight trafficLight) {
        long initialDelay = calculateDelay(trafficLight.getWeatherCondition(), trafficLight.getCurrentColor());
        scheduleColorChange(trafficLight, initialDelay);
    }

    public void scheduleAdjustedTrafficLightColorChange(TrafficLight trafficLight, double adjustmentFactor) {
        long initialDelay = (long) (calculateDelay(trafficLight.getWeatherCondition(), trafficLight.getCurrentColor()) * adjustmentFactor);
        scheduleColorChange(trafficLight, initialDelay);
    }

    private void scheduleColorChange(TrafficLight trafficLight, long delay) {
        Runnable task = () -> {
            TrafficLightColor nextColor = getNextColor(trafficLight.getCurrentColor());
            trafficLight.setCurrentColor(nextColor);
            trafficLightRepository.save(trafficLight);
            System.out.println("Changed to: " + nextColor + " for Traffic Light ID: " + trafficLight.getTrafficLightId());
            // Schedule the next color change
            long nextDelay = calculateDelay(trafficLight.getWeatherCondition(), nextColor);
            scheduleColorChange(trafficLight, nextDelay);
        };
        scheduledExecutorService.schedule(task, delay, TimeUnit.SECONDS);
    }

    private long calculateDelay(WeatherCondition weatherCondition, TrafficLightColor currentColor) {
        if (currentColor == TrafficLightColor.YELLOW) {
            return 3; // Yellow light always lasts for 3 seconds
        } else {
            return switch (weatherCondition) {
                case SUNNY -> 30;
                case RAINY -> 45;
                case FOGGY -> 60;
                default -> 30;
            };
        }
    }

    private TrafficLightColor getNextColor(TrafficLightColor currentColor) {
        return switch (currentColor) {
            case RED -> TrafficLightColor.GREEN;
            case GREEN -> TrafficLightColor.YELLOW;
            case YELLOW -> TrafficLightColor.RED;
        };
    }

    public void scheduleEmergencyMode(TrafficLight trafficLight) {
        Runnable task = () -> {
            trafficLight.setCurrentColor(TrafficLightColor.GREEN);
            trafficLightRepository.save(trafficLight);
            System.out.println("Emergency Mode: Changed to GREEN for 90 seconds for Traffic Light ID: " + trafficLight.getTrafficLightId());
            // Schedule the return to normal flow after 90 seconds
            scheduledExecutorService.schedule(() -> {
                trafficLight.setEmergencyMode(false);
                scheduleTrafficLightColorChange(trafficLight);
            }, 90, TimeUnit.SECONDS);
        };
        scheduledExecutorService.schedule(task, 0, TimeUnit.SECONDS);
    }

    public void schedulePedestrianMode(TrafficLight trafficLight) {
        Runnable task = () -> {
            trafficLight.setCurrentColor(TrafficLightColor.RED);
            trafficLightRepository.save(trafficLight);
            System.out.println("Emergency Mode: Changed to RED for 360 seconds for Traffic Light ID: " + trafficLight.getTrafficLightId());
            // Schedule the return to normal flow after 360 seconds
            scheduledExecutorService.schedule(() -> {
                trafficLight.setPedestrianMode(false);
                scheduleTrafficLightColorChange(trafficLight);
            }, 360, TimeUnit.SECONDS);
        };
        scheduledExecutorService.schedule(task, 0, TimeUnit.SECONDS);
    }
}
