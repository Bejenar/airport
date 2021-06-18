package md.ceiti.practica.airport.route;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import md.ceiti.practica.airport.passenger.Passenger;

import javax.persistence.Entity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteDTO {

    private Long codeRoute;
    private String destination;
    private String planeClass;
    private Double price;
    private Long passengerCode;

    public static RouteDTO fromEntity(Route route) {
        return RouteDTO.builder()
            .codeRoute(route.getCodeRoute())
            .destination(route.getDestination())
            .planeClass(route.getPlaneClass())
            .price(route.getPrice())
            .passengerCode(route.getPassenger().getPassengerCode())
            .build();
    }

    public Route toEntity(Passenger passenger) {
        return Route.builder()
            .codeRoute(this.codeRoute)
            .destination(this.destination)
            .planeClass(this.planeClass)
            .price(this.price)
            .passenger(passenger)
            .build();
    }
}
