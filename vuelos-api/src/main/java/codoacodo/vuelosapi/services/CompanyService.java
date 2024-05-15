package codoacodo.vuelosapi.services;
import codoacodo.vuelosapi.CompanyException.CompanyException;
import codoacodo.vuelosapi.model.Company;
import codoacodo.vuelosapi.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public Optional<Company> getById(long id){
        Company company = companyRepository.findById(id).orElse(null);
        if(company != null){
            return Optional.of(company);
        }else{
            throw new CompanyException("No existe la compania con el id: " + id);
        }
    }

    public Company addCompany(Company company){
        return companyRepository.save(company);
    }

    public List<Company> addCompanyList (List<Company> companies){
        return companyRepository.saveAll(companies);
    }

    public String deleteCompany( long id){
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent() ){
            companyRepository.deleteById(id);
            return "Eliminada con exito";
        }else{
            throw new CompanyException("No existe la compania con el id: " + id);
        }
    }

    public Optional<Company> updateCompany (Long id, Company updatedCompany){
        Optional<Company> existingCompanyOptional = companyRepository.findById(id);
        if(existingCompanyOptional.isPresent()){
            Company existingCompany = existingCompanyOptional.get();
            existingCompany.setName(updatedCompany.getName());
            existingCompany.setBanner(updatedCompany.getBanner());
            companyRepository.save(existingCompany);
            return Optional.of(existingCompany);
        } else {
            throw new CompanyException("No existe la compania con el id: " + id);

        }
    }
}
