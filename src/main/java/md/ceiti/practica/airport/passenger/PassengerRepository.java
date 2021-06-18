package md.ceiti.practica.airport.passenger;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    List<Passenger> findAllByOrderBySurnameAsc();

    List<Passenger> findAllByPassengerCodeIn(List<Long> codes);
}
