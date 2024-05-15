package codoacodo.vuelosapi.controller;
import codoacodo.vuelosapi.model.Company;
import codoacodo.vuelosapi.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    CompanyService companyService;
    @CrossOrigin
    @GetMapping("/list")
    public List<Company> getAllCompanies(){
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public Optional<Company> getById(@PathVariable(name = "id") long id){
        return companyService.getById(id);
    }

    @PostMapping("/add")
    public Company addCompany(@RequestBody Company company){
        return companyService.addCompany(company);
    }

    @PostMapping("/addList")
    public List<Company> addCompanyList(@RequestBody List<Company> companies){
        return companyService.addCompanyList(companies);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCompany(@PathVariable long id){
        return companyService.deleteCompany(id);
    }

    @PutMapping("/update/{id}")
    public Optional<Company> updateCompany(@PathVariable long id, @RequestBody Company company){
        return companyService.updateCompany(id, company);
    }

}
