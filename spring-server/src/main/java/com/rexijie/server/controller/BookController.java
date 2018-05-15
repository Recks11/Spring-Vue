package com.rexijie.server.controller;

import com.rexijie.server.model.Book;
import com.rexijie.server.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/single/{id}")
    public Mono<Book> getBook(@PathVariable String id) {
        return bookService.findBookById(id);
    }

    @GetMapping(value = "/delayed/{delay}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody()
    public Flux<Book> getAllBooksWithInterval(@PathVariable("delay") Long delay) {
        log.info("serving data on /delay");
        return bookService.findAllBooksWithDelay(delay);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<Book> createBook(@RequestBody Book book) {
        return bookService.ceateAndSaveBook(book);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public Mono<Void> deleteBook(@PathVariable String id) {
        return bookService.deleteSingle(id);
    }
}
