package vn.techmaster.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.blogs.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
}
