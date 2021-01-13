package vn.techmaster.person.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.techmaster.person.repository.PersonRepositoryInterface;


@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private PersonRepositoryInterface personRepository;
    
    @GetMapping
    public String home(){
        return "home";
    }
    
    @GetMapping("/getall")
    public String getAll(Model model){
        model.addAttribute("listpeople", personRepository.getAll());
        return "listpeople";
    }
    @GetMapping("/list-sorted-people-by-name")
    public String getSortedPeopleByFullname(Model model){
        model.addAttribute("listpeople", personRepository.sortPeopleByFullNameReversed());
        return "listpeople";
    }
    @GetMapping("/jobs")
    public String getListJobs(Model model){
        model.addAttribute("jobs", personRepository.getSortedJobs());
        return "jobs";
    }
    @GetMapping("/cities")
    public String getCities(Model model){
        model.addAttribute("cities", personRepository.getSortedCities());
        return "cities";
    }

    @GetMapping("/group-people-by-city")
    public String getGroupPeopleByCity(Model model){
        model.addAttribute("groupPeopleByCity", personRepository.groupPeopleByCity());
        return "peopleByCity";
    }
    @GetMapping("/group-job-by-count")
    public String getGroupJobByCount(Model model){
        model.addAttribute("groupJob", personRepository.groupJobByCount());
        return "groupJobs";
    }

    @GetMapping("/top-5-jobs")
    public String getFindTop5Jobs(Model model){
        model.addAttribute("top", personRepository.findTop5Jobs());
        return "top5";
    }

    @GetMapping("/top-5-cities")
    public String getFindTop5Cities(Model model){
        model.addAttribute("top", personRepository.findTop5Cities());
        return "top5";
    }

    @GetMapping("/top-5-highest-salary-city")
    public String getTop5HighestSalaryCities(Model model){
        model.addAttribute("top", personRepository.top5HighestSalaryCities());
        return "top5";
    }

    @GetMapping("/list-top-job-in-city")
    public String getFindTopJobInCity(Model model){
        model.addAttribute("listTopJob", personRepository.findTopJobInCity());
        return "listTopJobInCity";
    }

    @GetMapping("/avg-Job-salary")
    public String getAvgJobSalary(Model model){
        model.addAttribute("avg", personRepository.avgJobSalary());
        return "avg";
    }

    @GetMapping("/avg-Job-age")
    public String getAvgJobAge(Model model){
        model.addAttribute("avg", personRepository.avgJobAge());
        return "avg";
    }

    @GetMapping("/avg-city-age")
    public String getAvgCityAge(Model model){
        model.addAttribute("avg", personRepository.avgCityAge());
        return "avg";
    }

    @GetMapping("/find-5-cities-have-most-job")
    public String getFind5CitiesHaveSpecificJob(){
        return "specificJob";
    }
    @PostMapping("/find5citiesHaveMostSpecificJob")
    public String postFind5CitiesHaveSpecificJob(@RequestParam("job") String job, Model model){
        model.addAttribute("job", job);
        model.addAttribute("listJob", personRepository.find5CitiesHaveMostSpecificJob(job));
        return "specificJob";
    }
}
