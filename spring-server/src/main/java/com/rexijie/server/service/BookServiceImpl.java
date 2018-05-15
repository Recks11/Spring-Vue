package com.rexijie.server.service;

import com.rexijie.server.model.Book;
import com.rexijie.server.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Mono<Book> ceateAndSaveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Mono<Book> findBookById(String id) {
        return bookRepository.findBookById(id);
    }

    @Override
    public Flux<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Flux<Book> findAllBooksWithDelay(Long delay) {
        return bookRepository.findAll().delayElements(Duration.ofSeconds(delay));
    }

    @Override
    public Mono<Void> saveAll(Flux<Book> books) {
        return bookRepository.saveAll(books.toIterable())
                .doOnNext(book -> {
                    System.out.println("saving Book: "+ book.toString());
                }).then();
    }

    @Override
    public Mono<Void> deleteAll() {
        return bookRepository.deleteAll();
    }
}
