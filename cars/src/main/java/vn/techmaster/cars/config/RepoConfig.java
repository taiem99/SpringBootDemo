package vn.techmaster.cars.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import vn.techmaster.cars.repository.CarDao;

@Configuration
public class RepoConfig {
    
    @Bean
    public CarDao carDao(){
        return new CarDao("car.csv");
    }
}
