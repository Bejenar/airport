package md.ceiti.practica.airport;

import md.ceiti.practica.airport.passenger.PassengerRepository;
import md.ceiti.practica.airport.passenger.Passenger;
import md.ceiti.practica.airport.route.Route;
import md.ceiti.practica.airport.route.RouteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AirportApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirportApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PassengerRepository passengerRepository, RouteRepository routeRepository) {
        return args -> {
            Passenger passenger = Passenger.builder()
                .name("Ivan")
                .surname("Ivanov")
                .phone("+37369123321")
                .build();
            passengerRepository.save(passenger);


            Route route = Route.builder()
                .destination("Berlin")
                .planeClass("BUSINESS")
                .price(2000.0)
                .passenger(passenger)
                .build();

            routeRepository.save(route);
        };
    }

}
