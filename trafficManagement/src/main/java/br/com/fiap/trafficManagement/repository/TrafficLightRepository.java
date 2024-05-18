package br.com.fiap.trafficManagement.repository;

import br.com.fiap.trafficManagement.model.TrafficLight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrafficLightRepository extends JpaRepository<TrafficLight, Long> {
    //findByLocation
}
