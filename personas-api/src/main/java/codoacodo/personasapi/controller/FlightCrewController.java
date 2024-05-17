package codoacodo.personasapi.controller;

import codoacodo.personasapi.model.FlightCrew;
import codoacodo.personasapi.repository.FlightCrewRepository;
import codoacodo.personasapi.service.FlightCrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flightCrew")
public class FlightCrewController {
    @Autowired
    FlightCrewService flightCrewService;

    @GetMapping("/list")
    public List<FlightCrew> list (){
        return flightCrewService.list();
    }

    @GetMapping("/findById/{dni}")
    public FlightCrew findById(@PathVariable(name = "dni") long dni){
        return flightCrewService.findById(dni);
    }

    @GetMapping("/findByIdBody")
    public FlightCrew findByIdBody(@RequestBody long dni){
        return flightCrewService.findById(dni);
    }

    @GetMapping("/findAllById")
    public List<FlightCrew> findAllById(@RequestBody List<Long> crewIds){
        return flightCrewService.findAllById(crewIds);
    }

    @PostMapping("/add")
    public FlightCrew add(@RequestBody FlightCrew flightCrew){
        return flightCrewService.add(flightCrew);
    }
    @PostMapping("/addList")
    public List<FlightCrew> addList(@RequestBody List<FlightCrew> flightCrew){
        return flightCrewService.addList(flightCrew);
    }

    @PutMapping("/update/{dni}")
    public FlightCrew update (@RequestBody FlightCrew flightCrew, @PathVariable(name ="dni") long dni){
        return flightCrewService.update(flightCrew, dni);
    }

    @PutMapping("/updateAll")
    public List<FlightCrew> updateAll(@RequestBody List<FlightCrew> flightCrewList ){
        return flightCrewService.updateAll(flightCrewList);
    }

    @DeleteMapping("/delete/{dni}")
    public String delete(@PathVariable(name ="dni") long dni){
        return flightCrewService.delete(dni);
    }

    @DeleteMapping("/deleteList")
    public String deleteList(@RequestBody List<Long> dni){
        return flightCrewService.deleteList(dni);
    }
}
