package vn.techmaster.cars.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

// import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Car {
    @JsonIgnore
    private int id;
    private String model;
    private String manufacturer;
    private int price;
    private int sales;
    public String photo;

    @Override
    public String toString() {
        return "Car [id=" + id + ", manufacturer=" + manufacturer + ", model=" + model + ", photo=" + photo + ", price="
                + price + ", sales=" + sales + "]";
    }

    public boolean mathWithKeywords(String keyword){
        String searchKeys = keyword.toLowerCase();
        return model.toLowerCase().contains(searchKeys) || manufacturer.toLowerCase().contains(searchKeys)
        || searchKeys.contains(model.toLowerCase()) || searchKeys.contains(manufacturer.toLowerCase());
    }
    
}
