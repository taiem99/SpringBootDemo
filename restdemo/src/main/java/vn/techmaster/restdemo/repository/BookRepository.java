package vn.techmaster.restdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.restdemo.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
}
