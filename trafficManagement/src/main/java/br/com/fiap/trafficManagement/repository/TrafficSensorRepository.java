package br.com.fiap.trafficManagement.repository;

import br.com.fiap.trafficManagement.model.TrafficSensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrafficSensorRepository extends JpaRepository<TrafficSensor, Long> {
}
