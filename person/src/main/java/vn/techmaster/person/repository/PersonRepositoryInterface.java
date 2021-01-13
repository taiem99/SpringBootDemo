package vn.techmaster.person.repository;

import java.util.HashMap;
import java.util.List;

import vn.techmaster.person.model.Person;

public interface PersonRepositoryInterface {
    List<Person> getAll(); // liet ke danh sach
    
    List<Person> sortPeopleByFullNameReversed(); // liet ke danh theo fullname z->a

    List<String> getSortedJobs(); // --> liet ke job tu a ->z

    List<String> getSortedCities(); // liet ke city tu a->z

    HashMap<String, List<Person>> groupPeopleByCity(); // gom people cung thanh pho vao 1 nhom

    HashMap<String, Integer> groupJobByCount();

    HashMap<String, Integer> findTop5Jobs();

    HashMap<String, Integer> findTop5Cities();

    HashMap<String, Float> avgJobSalary();

    HashMap<String, Double> top5HighestSalaryCities();

    HashMap<String, Double> avgJobAge();

    HashMap<String, Double> avgCityAge();

    List<String> find5CitiesHaveMostSpecificJob(String job);
    HashMap<String, String> findTopJobInCity();
}
