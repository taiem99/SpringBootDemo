package vn.techmaster.cars.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.cars.model.Car;
import vn.techmaster.cars.repository.CarDao;
@RequestMapping("/cars")
@Controller
public class CarController {
    @Autowired
    private CarDao carDao;

    @GetMapping
    public String listAll(Model model){
        model.addAttribute("cars", carDao.getAll());
        return "allcars";
    } 
    
    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model){
        Optional<Car> car = carDao.get(id);
        if(car.isPresent()){
            model.addAttribute("cars", car.get());
        }
        return "car";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("car", new Car());
        return "form";
    }

    @PostMapping("/save")
    public String save(Car car, BindingResult result){
        if(result.hasErrors()){
            return "form";
        } 
        if (car.getId() > 0) { 
            carDao.update(car);
        } else { 
            carDao.add(car);
        }
        
        return "redirect:/cars";
    }

    @GetMapping("/edit/{id}")
    public String editCarById(@PathVariable("id") int id, Model model){
        Optional<Car> result = carDao.get(id);
        if(result.isPresent()){
            model.addAttribute("car", result.get());
        }
        return "form";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") int id){
        carDao.deleteById(id);
        return "redirect:/cars";
    }
}
