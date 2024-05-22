package br.com.fiap.trafficManagement.repository;

import br.com.fiap.trafficManagement.model.TrafficLight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrafficLightRepository extends JpaRepository<TrafficLight, Long> {

    @Query("SELECT tl FROM TrafficLight tl WHERE tl.location.latitude BETWEEN :minLatitude AND :maxLatitude AND tl.location.longitude BETWEEN :minLongitude AND :maxLongitude")
    List<TrafficLight> findInLocationRange(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude);

}
