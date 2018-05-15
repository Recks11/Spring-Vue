package com.rexijie.server.service;

import com.rexijie.server.model.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

    Mono<Book> ceateAndSaveBook(Book book);
    Mono<Book> findBookById(String id);
    Flux<Book> findAllBooks();
    Flux<Book> findAllBooksWithDelay(Long delay);
    Mono<Void> saveAll(Flux<Book> book);
    Mono<Void> deleteAll();
    Mono<Void> deleteSingle(String id);
}
