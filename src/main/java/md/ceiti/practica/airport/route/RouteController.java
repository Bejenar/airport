package md.ceiti.practica.airport.route;

import lombok.RequiredArgsConstructor;
import md.ceiti.practica.airport.passenger.PassengerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteRepository routeRepository;
    private final PassengerRepository passengerRepository;

    @GetMapping("/list")
    public String listRoutes(Model theModel) {

        // get Routes from db
        List<Route> passangers = routeRepository.findAll();

        // add to the spring model
        theModel.addAttribute("routes", passangers);

        return "routes/list-routes";
    }

    @GetMapping("/list/{passengerCode}")
    public String listRoutes(Model theModel, @PathVariable("passengerCode") Long passengerCode) {

        // get Routes from db
        List<Route> passangers = routeRepository.findByPassengerCode(passengerCode);

        // add to the spring model
        theModel.addAttribute("routes", passangers);

        return "routes/list-routes";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        RouteDTO theRoute = new RouteDTO();

        theModel.addAttribute("route", theRoute);
        theModel.addAttribute("passengers", passengerRepository.findAll());

        return "routes/routes-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("routeId") Long theId,
                                    Model theModel) {

        // get the Route from the service
        Route theRoute = routeRepository.findById(theId).get();
        RouteDTO routeDTO = RouteDTO.fromEntity(theRoute);

        // set Route as a model attribute to pre-populate the form
        theModel.addAttribute("route", routeDTO);
        theModel.addAttribute("passengers", passengerRepository.findAll());

        // send over to our form
        return "routes/routes-form";
    }


    @PostMapping("/save")
    @Transactional
    public String saveRoute(@ModelAttribute("route") RouteDTO theRoute) {

        // save the Route
        //routeRepository.getById(theRoute.getCodeRoute());
        Route route = theRoute.toEntity(passengerRepository.findById(theRoute.getPassengerCode()).get());
        routeRepository.save(route);

        // use a redirect to prevent duplicate submissions
        return "redirect:/routes/list";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("routeId") Long theId) {

        // delete the Route
        routeRepository.deleteById(theId);

        // redirect to /routes/list
        return "redirect:/routes/list";

    }
}
