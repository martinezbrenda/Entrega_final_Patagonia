package codoacodo.personasapi.controller;

import codoacodo.personasapi.Exception.PassengerException;
import codoacodo.personasapi.model.FlightCrew;
import codoacodo.personasapi.model.Passenger;
import codoacodo.personasapi.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/passenger")
public class PassengerController {
    @Autowired
    PassengerService passengerService;

    @GetMapping("/list")
    public List<Passenger> list() {
        return passengerService.list();
    }
    @GetMapping("/findById/{dni}")
    public Passenger findById(@PathVariable(name = "dni") long dni) {
        return passengerService.findById(dni);
    }
    @PostMapping("/add")
    public Passenger add(@RequestBody Passenger passenger) {

        return passengerService.add(passenger);
    }
    @PostMapping("/addList")
    public List<Passenger> addList(@RequestBody  List<Passenger> passenger) {

        return passengerService.addList(passenger);
    }
    @PutMapping("/update/{dni}")
    public Passenger update(@RequestBody Passenger passenger, @PathVariable(name ="dni") long dni) {
        return passengerService.update(passenger,dni);
    }

    @DeleteMapping("/delete/{dni}")
    public String delete(@PathVariable(name ="dni") long dni){
        return passengerService.delete(dni);
    }

    @DeleteMapping("/deleteList")
    public String deleteList(@RequestBody List<Long> dni){
        return passengerService.deleteList(dni);
    }
}
