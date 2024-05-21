package br.com.fiap.trafficManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "TBL_TRAFFIC_LIGHTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@DynamicInsert
public class TrafficLight {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_TRAFFIC_LIGHT"
    )
    @SequenceGenerator(
            name = "SEQ_TRAFFIC_LIGHT",
            sequenceName = "SEQ_TRAFFIC_LIGHT",
            allocationSize = 1
    )
    @Column(name = "id_traffic_light")
    private Long trafficLightId;

    @Embedded
    @NotNull
    private Location location;

    @Column(name = "current_color")
    @Enumerated(EnumType.STRING)
    private TrafficLightColor currentColor = TrafficLightColor.RED;

    @Column(name = "weather_condition")
    @Enumerated(EnumType.STRING)
    private WeatherCondition weatherCondition = WeatherCondition.SUNNY;

    @Column(name = "pedestrian_mode")
    private boolean pedestrianMode;

    @Column(name = "emergency_mode")
    private boolean emergencyMode;

    @Column(name = "fault_status")
    private boolean faultStatus;

    @Column(name = "current_status")
    private boolean currentStatus;

}