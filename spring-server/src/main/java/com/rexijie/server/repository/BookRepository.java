package com.rexijie.server.repository;

import com.rexijie.server.model.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Mono<Book> findBookById(String id);
}
