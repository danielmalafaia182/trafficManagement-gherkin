package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class TrafficLightModel {
    @Expose
    private Long trafficLightId;
    @Expose
    private double latitude;
    @Expose
    private double longitude;
}
