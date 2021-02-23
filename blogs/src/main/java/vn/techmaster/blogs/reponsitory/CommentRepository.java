package vn.techmaster.blogs.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.techmaster.blogs.model.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
}
