package md.ceiti.practica.airport.passenger;

import lombok.RequiredArgsConstructor;
import md.ceiti.practica.airport.route.Route;
import md.ceiti.practica.airport.route.RouteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerRepository passengerRepository;
    private final RouteRepository routeRepository;

    @GetMapping("/list")
    public String listpassengers(Model theModel) {

        // get passengers from db
        List<Passenger> passangers = passengerRepository.findAll();

        // add to the spring model
        theModel.addAttribute("passengers", passangers);

        return "passengers/list-passengers";
    }

    @PostMapping("/query")
    public String queryPassenger(@ModelAttribute("route") Route route, Model theModel) {

        List<Route> routes = routeRepository.findByDestinationAndPlaneClass(route.getDestination(), route.getPlaneClass());
        List<Passenger> passengers = passengerRepository.findAllByPassengerCodeIn(routes
            .stream()
            .map(r -> r.getPassenger().getPassengerCode())
            .collect(Collectors.toList()));
        // add to the spring model
        theModel.addAttribute("passengers", passengers);

        return "passengers/list-passengers";
    }

    @GetMapping("/showFormForQuery")
    public String showFormForQuery(Model theModel) {

        // create model attribute to bind form data
        Route route = new Route();

        theModel.addAttribute("route", route);

        return "passengers/find-passenger";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Passenger thePassenger = new Passenger();

        theModel.addAttribute("passenger", thePassenger);

        return "passengers/passenger-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("passengerId") Long theId,
                                    Model theModel) {

        // get the Passenger from the service
        Passenger thePassenger = passengerRepository.findById(theId).get();

        // set Passenger as a model attribute to pre-populate the form
        theModel.addAttribute("passenger", thePassenger);

        // send over to our form
        return "passengers/passenger-form";
    }


    @PostMapping("/save")
    public String savePassenger(@ModelAttribute("passenger") Passenger thePassenger) {

        // save the Passenger
        passengerRepository.save(thePassenger);

        // use a redirect to prevent duplicate submissions
        return "redirect:/passengers/list";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("passengerId") Long theId) {

        // delete the Passenger
        passengerRepository.deleteById(theId);

        // redirect to /passengers/list
        return "redirect:/passengers/list";

    }
}
