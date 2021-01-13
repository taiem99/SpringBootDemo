package vn.techmaster.person;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import vn.techmaster.person.model.Person;
import vn.techmaster.person.repository.PersonRepository;


@SpringBootTest
class PersonApplicationTests {
	@Autowired
	PersonRepository personRepository;
	
	@Test
	public void getAll(){
		List<Person> people = personRepository.getAll();
		assertThat(people).hasSize(20);
	}

	@Test
	public void sortPeopleByFullNameReversedTest(){
		List<Person> people = personRepository.sortPeopleByFullNameReversed();
		assertThat(people).hasSize(20);
	}

	@Test
	public void getSortedJobsTest(){
		List<String> people = personRepository.getSortedJobs();
		assertThat(people).isSorted();
		assertThat(people).hasSizeLessThan(20);
	}

	@Test
	public void findTop5JobsTest(){
		HashMap<String, Integer> result = personRepository.findTop5Jobs();
		assertThat(result).hasSize(5);
		

	}
}
