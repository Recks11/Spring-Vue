package com.rexijie.server.bootstrap;

import com.rexijie.server.model.Book;
import com.rexijie.server.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class FakeDataInitializer implements CommandLineRunner {

    private final BookService bookService;

    @Autowired
    public FakeDataInitializer(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        bookService.deleteAll().subscribe();
        addBooks();
    }

    private void addBooks() {

        Book cloudNative = Book.builder().publisher("Orilley").title("Cloud Native Java").year("2017").build();
        Book happyCity = Book.builder().publisher("Orilley").title("Happy City").year("2017").build();
        Book NSA = Book.builder().publisher("Orilley").title("Network Security Assessment").year("2016").build();
        Book book4 = Book.builder().publisher("Orilley").title("Return of the Spandex").year("2013").build();
        Book jp = Book.builder().publisher("Orilley").title("Jurrastic park").year("2008").build();
        Book comic = Book.builder().publisher("Orilley").title("Blackest Night").year("2013").build();

        bookService.saveAll(Flux.just(cloudNative, happyCity, NSA, book4, jp, comic)).subscribe();
        log.info("Finished initializing data");
    }
}
