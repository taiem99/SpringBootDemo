package vn.techmaster.blogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import vn.techmaster.blogs.repository.UserRepository;

@DataJpaTest
public class UserRepoTest {
    @Autowired
    private UserRepository userRepo;

    @Test
    void findByEmail(){
        var user = userRepo.findByEmail("bob@gmail.com");
        assertThat(user).isPresent();
        assertThat(user.get().getEmail()).isEqualTo("bob@gmail.com");
    }
}
