package codoacodo.vuelosapi.configuration;

import codoacodo.vuelosapi.model.Dolar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

@Configuration
public class FlightConfiguration {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public List<Dolar> fetchDolares(){
        RestTemplate restTemplate = restTemplate();
        String apiURL = "https://dolarapi.com/v1/dolares";
        Dolar[] dolaresArray = restTemplate.getForObject(apiURL, Dolar[].class);
        return Arrays.asList(dolaresArray);
    }

    public Dolar[] fetchAllDolars(){
        RestTemplate restTemplate = restTemplate();
        String apiURL = "https://dolarapi.com/v1/dolares";
        return restTemplate.getForEntity(apiURL,Dolar[].class).getBody();
    }
    public Dolar fetchDolarCasa(String casa){
        RestTemplate restTemplate = restTemplate();
        String apiURL = "https://dolarapi.com/v1/dolares/";
        String finalApiURL = apiURL + casa;
        return restTemplate.getForObject(finalApiURL, Dolar.class);
    }

}
