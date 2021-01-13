package vn.techmaster.person.repository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import vn.techmaster.person.model.Person;

@Repository
public class PersonRepository implements PersonRepositoryInterface {

    private List<Person> people;

    @Autowired
    public PersonRepository(@Value("${csvFile}") String csvFile){
        people = new ArrayList<>();
        loadData(csvFile);
    }

    private void loadData(String csvFile){
        try{
            File file = ResourceUtils.getFile("classpath:static/" + csvFile);
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = CsvSchema.emptySchema().withHeader();
            ObjectReader oReader = mapper.readerFor(Person.class).with(schema);
            Reader reader = new FileReader(file);
            MappingIterator<Person> mi = oReader.readValues(reader);
            while(mi.hasNext()){
                Person person = mi.next();
                people.add(person);
            }
        } catch (IOException e){
            System.out.println(e);
        }
    }

    @Override
    public List<Person> getAll() {
        return people;
    }

    @Override
    public List<Person> sortPeopleByFullNameReversed() {
        return people.stream().sorted(Comparator.comparing(Person::getFullname).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<String> getSortedJobs() {
        List<String> result = new ArrayList<>();
        for (Person person:people){
            if(!result.contains(person.getJob())){
                result.add(person.getJob());
            }
        } 
        Collections.sort(result);
        return result;
    }

    @Override
    public List<String> getSortedCities() {
        List<String> result = new ArrayList<>();
        for (Person person:people){
           if(!result.contains(person.getCity())){
                result.add(person.getCity());
           }
        } 
        Collections.sort(result);
        return result;
    }

    @Override
    public HashMap<String, List<Person>> groupPeopleByCity() {
        HashMap<String, List<Person>> result = new HashMap<>();
        for(Person person:people){
            if(result.containsKey(person.getCity())){
                result.get(person.getCity()).add(person);
            } else {
                result.put(person.getCity(), new ArrayList<>());
                result.get(person.getCity()).add(person);
            }
        }
    
        return result.entrySet().stream().sorted(Map.Entry.comparingByKey())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
        (oldValue, newValue)-> oldValue, LinkedHashMap::new));
    }

    @Override
    public HashMap<String, Integer> groupJobByCount() {
        HashMap<String, Integer> result = new HashMap<>();
        for(Person person:people){
            if(result.containsKey(person.getJob())){
                result.put(person.getJob(), result.get(person.getJob()) + 1);
            } else{
                result.put(person.getJob(), 1);
            }
        }
        
        return result.entrySet().stream().sorted(Map.Entry.comparingByValue())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, 
        (oldValue, newValue)-> oldValue, LinkedHashMap::new));
    }

    @Override
    public HashMap<String, Integer> findTop5Jobs() {
        HashMap<String, Integer> result = groupJobByCount();
       
        return  result.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .limit(5).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, 
        (oldValue, newValue)-> oldValue, LinkedHashMap::new));
    }

    @Override
    public HashMap<String, Integer> findTop5Cities() {
        HashMap<String, Integer> result = new HashMap<>();
        for(Person person:people){
            if(result.containsKey(person.getCity())){
                result.put(person.getCity(), result.get(person.getCity()) + 1);
            } else{
                result.put(person.getCity(), 1);
            }
        }
        return result.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(5)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, 
        (oldValue, newValue)-> oldValue, LinkedHashMap::new));
    }

    @Override
    public HashMap<String, Float> avgJobSalary() {
        HashMap<String, Integer> result = groupJobByCount();
        HashMap<String, Float> result1 = new HashMap<>();
        for(String tmp: result.keySet()){
            result1.put(tmp, (float)sumJobSalary(tmp)/result.get(tmp));
        }
        return result1.entrySet().stream().sorted(Map.Entry.comparingByKey())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
        (oldValue, newValue)-> oldValue, LinkedHashMap::new));
       
    }
    public int sumJobSalary(String job){
        int sum = 0;
        for(Person person:people){
            if(person.getJob().equals(job)){
                sum += person.getSalary();
            }
        }
        return sum;
    }

    @Override
    public HashMap<String, Double> top5HighestSalaryCities() {
        return people.stream().collect(Collectors.groupingBy(Person::getCity, Collectors.averagingDouble(Person::getSalary)))
        .entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .limit(5).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
        (o1, o2) -> o1,LinkedHashMap::new));
        
    }

    @Override
    public HashMap<String, Double> avgJobAge() {
        HashMap<String, Double> result = (HashMap<String, Double>) people.stream()
            .collect(Collectors.groupingBy(Person::getJob, Collectors.averagingDouble(Person::getAge)));
            
        return result.entrySet().stream().sorted(Map.Entry.comparingByKey())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
        (oldValue, newValue)-> oldValue, LinkedHashMap::new));
    }

    @Override
    public HashMap<String, Double> avgCityAge() {
        HashMap<String, Double> result = (HashMap<String, Double>) people.stream()
                .collect(Collectors.groupingBy(Person::getCity, Collectors.averagingDouble(Person::getAge)));
        return result.entrySet().stream().sorted(Map.Entry.comparingByKey())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
        (oldValue, newValue)-> oldValue, LinkedHashMap::new));
    }

    @Override
    public List<String> find5CitiesHaveMostSpecificJob(String job) {
        List<String> result = people.stream().filter(e -> e.getJob().toLowerCase().contains(job.toLowerCase()))
            .collect(Collectors.groupingBy(Person::getCity, Collectors.counting()))
            .entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(5)
            .map(e->e.getKey()).collect(Collectors.toList());
        return result;
    }
    
    @Override
    public HashMap<String, String> findTopJobInCity(){
        return (HashMap<String, String>) people.stream().collect(Collectors.groupingBy(Person::getCity, 
        Collectors.collectingAndThen(Collectors.groupingBy(Person::getJob, Collectors.counting())
        , map -> map.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey()))); 
    }
    
}
