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
    @DecimalMin("-9999.99") @DecimalMax("9999.99")
    private double latitude;

    @DecimalMin("-9999.99") @DecimalMax("9999.99")
    private double longitude;
}
