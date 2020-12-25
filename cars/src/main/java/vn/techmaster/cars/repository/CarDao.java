package vn.techmaster.cars.repository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.springframework.util.ResourceUtils;

import vn.techmaster.cars.model.Car;

public class CarDao extends Dao<Car> {
    public CarDao(String csvFile){
        this.readCSV(csvFile);
    }

    @Override
    public List<Car> getAll() {
        return collections;
    }

    @Override
    public Optional<Car> get(int id) {
        
        return collections.stream().filter(u -> u.getId() == id).findFirst();
    }

    @Override
    public void add(Car car) {
        int id;
        if (collections.isEmpty()) {
          id = 1;
        } else {
          Car lastCar = collections.get(collections.size() - 1);
          id = lastCar.getId() + 1;      
        }
        car.setId(id);
        collections.add(car);

    }

    @Override
    public void update(Car car) {
        get(car.getId()).ifPresent(exitCar -> {
            exitCar.setModel(car.getModel());
            exitCar.setManufacturer(car.getManufacturer());
            exitCar.setPrice(car.getPrice());
            exitCar.setSales(car.getSales());
            exitCar.setPhoto(car.getPhoto());
        });

    } 

    @Override
    public void delete(Car car) {
        deleteById(car.getId());

    }

    @Override
    public void deleteById(int id) {
        get(id).ifPresent(car -> collections.remove(car));
    }

    @Override
    public void readCSV(String csvFile) {
        try {
            File file = ResourceUtils.getFile("classpath:static/" + csvFile);
            CsvMapper mapper = new CsvMapper(); // Dùng để ánh xạ cột trong CSV với từng trường trong POJO
            CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator('|'); // Dòng đầu tiên sử dụng làm Header
            ObjectReader oReader = mapper.readerFor(Car.class).with(schema); // Cấu hình bộ đọc CSV phù hợp với kiểu
            Reader reader = new FileReader(file);
            MappingIterator<Car> mi = oReader.readValues(reader); // Iterator đọc từng dòng trong file
            while (mi.hasNext()) {
              Car car = mi.next();
              this.add(car);
            }
          } catch (IOException e) {
            System.out.println(e); 
          }

    }

    @Override
    public List<Car> searchByKeywords(String keywords) {
        return collections.stream().filter(car -> car.mathWithKeywords(keywords)).collect(Collectors.toList());
    }
    
}
