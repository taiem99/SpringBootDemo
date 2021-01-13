package vn.techmaster.person.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class Person {
    
    private int id;
    private String fullname;
    private String job;
    private String gender;
    private String city;
    private int salary;
    private Date birthday;
    private static final SimpleDateFormat dateFormator = new SimpleDateFormat("yyyy/MM/dd");

    //Getter and Setter

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public void setFullname(String fullname){
        this.fullname = fullname;
    }
    public String getFullname(){
        return fullname;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        try{
            this.birthday = dateFormator.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }    
    public int getAge(){
        LocalDate birthDay = getBirthday().toInstant().atZone(ZoneId.systemDefault())
        .toLocalDate();
        return Period.between(birthDay, LocalDate.now()).getYears();
    }
}
