package br.com.fiap.trafficManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "TBL_TRAFFIC_SENSORS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@DynamicInsert
public class TrafficSensor {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_TRAFFIC_SENSOR"
    )
    @SequenceGenerator(
            name = "SEQ_TRAFFIC_SENSOR",
            sequenceName = "SEQ_TRAFFIC_SENSOR",
            allocationSize = 1
    )
    @Column(name = "id_traffic_sensor")
    private Long trafficSensorId;

    @Embedded
    @NotNull
    private Location location;

    @Column(name = "traffic_density")
    @Min(0)
    private int trafficDensity = 0;

    @Column(name = "fault_status")
    private boolean faultStatus;

    @Column(name = "current_status")
    private boolean currentStatus;

    public TrafficSensor(int trafficDensity){
        this.trafficDensity = trafficDensity;
    }

}
