package vn.techmaster.blogs.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.techmaster.blogs.model.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByUserId(Long user_id);
    
}
