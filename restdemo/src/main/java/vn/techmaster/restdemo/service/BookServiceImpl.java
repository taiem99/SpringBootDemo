package vn.techmaster.restdemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import vn.techmaster.restdemo.model.Book;
import vn.techmaster.restdemo.model.BookDto;
import vn.techmaster.restdemo.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepo;

    @Override
    public List<Book> findAll() {
        return bookRepo.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepo.findById(id);
    }

    @Override
    public Book save(BookDto book) {
        Book newBook = new Book(book.getTitle(), book.getAuthor());
        return bookRepo.save(newBook);
    }

    @Override
    public void update(Long id, BookDto book) {
       Book updateBook = new Book(book.getTitle(), book.getAuthor());
       Optional<Book> optionalBook = bookRepo.findById(id);
       if(optionalBook.isPresent()){
           bookRepo.save(updateBook);
       } else {
           throw new ResourceNotFoundException();
       }
    }

    @Override
    public void updateTitle(Long id, String title) {
        Optional<Book> optionalBook = bookRepo.findById(id);
        if(optionalBook.isPresent()){
            Book book = optionalBook.get();
            book.setTitle(title);
            bookRepo.save(book);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public void deleteById(Long id) {
        Optional<Book> book = bookRepo.findById(id);
        if(book.isPresent()){
            bookRepo.delete(book.get());
        } else {
            throw new ResourceNotFoundException();
        }
    }
    
}
