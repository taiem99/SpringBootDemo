package vn.techmaster.restdemo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.restdemo.model.Book;
import vn.techmaster.restdemo.model.BookDto;
import vn.techmaster.restdemo.service.BookService;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> findAllBooks(){
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("books/{bookId}")
    public ResponseEntity<Book> findBookById(@PathVariable Long bookId){
        Optional<Book> book = bookService.findById(bookId);
        if(book.isPresent()){
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody BookDto book){
        Book newBook = bookService.save(book);
        try {
            return ResponseEntity.created(new URI("/api/books/" + newBook.getId())).body(newBook);
        } catch(URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/books/{bookId}")
    public ResponseEntity<Void> updateBook(@RequestBody BookDto book,@PathVariable  Long bookId){
        try {
            bookService.update(bookId, book);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId){
        try {
            bookService.deleteById(bookId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/books/{bookId}")
    public ResponseEntity<Void> updateBookTitle(@RequestBody String title,@PathVariable Long bookId){
        try {
            bookService.updateTitle(bookId, title);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
