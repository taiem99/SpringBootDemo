package vn.techmaster.blogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import vn.techmaster.blogs.service.PostService;

@Component
public class ApplicationRunner implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Override
    public void run(String... args) throws Exception {
        postService.generateSampleData();
    }
    
}
