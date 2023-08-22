package com.tutorial.bookservice.queries.projection;

import com.tutorial.bookservice.commands.data.Book;
import com.tutorial.bookservice.commands.data.BookRepository;
import com.tutorial.bookservice.commands.model.BookRestModel;
import com.tutorial.bookservice.queries.queries.GetBooksQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookProjection {

    private BookRepository bookRepository;

    public BookProjection(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @QueryHandler
    public List<BookRestModel> handle(GetBooksQuery getBooksQuery) {
        List<Book> books =
                bookRepository.findAll();

        List<BookRestModel> bookRestModels =
                books.stream()
                        .map(book -> BookRestModel
                                .builder()
                                .title(book.getTitle())
                                .author(book.getAuthor())
                                .studentId(book.getStudentId())
                                .build())
                        .collect(Collectors.toList());

        return bookRestModels;
    }
}
