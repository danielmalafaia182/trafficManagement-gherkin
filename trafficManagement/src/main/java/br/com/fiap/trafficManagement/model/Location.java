package br.com.fiap.trafficManagement.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Location {
    @DecimalMin("-90.0") @DecimalMax("90.0")
    private double latitude;

    @DecimalMin("-180.0") @DecimalMax("180.0")
    private double longitude;
}
