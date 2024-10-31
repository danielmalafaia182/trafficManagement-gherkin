package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class TrafficSensorModel {
    @Expose
    private Long trafficSensorId;
    @Expose
    private double latitude;
    @Expose
    private double longitude;
}
