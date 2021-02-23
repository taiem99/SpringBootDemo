package vn.techmaster.blogs.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.techmaster.blogs.model.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    
}
