package md.ceiti.practica.airport.route;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query(nativeQuery = true, value = "SELECT * from route where passenger_passenger_code = :code")
    List<Route> findByPassengerCode(Long code);

    List<Route> findByDestinationAndPlaneClass(String destination, String planeClass);
}
