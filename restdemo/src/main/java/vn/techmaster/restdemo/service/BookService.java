package vn.techmaster.restdemo.service;

import java.util.List;
import java.util.Optional;

import vn.techmaster.restdemo.model.Book;
import vn.techmaster.restdemo.model.BookDto;

public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Book save(BookDto book);
    void update(Long id, BookDto book);
    void updateTitle(Long id, String title);
    void deleteById(Long id);
}
