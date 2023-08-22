package com.tutorial.bookservice.queries.controller;

import com.tutorial.bookservice.commands.model.BookRestModel;
import com.tutorial.bookservice.queries.queries.GetBooksQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookQueryController {

    private QueryGateway queryGateway;

    public BookQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<BookRestModel> getAllBooks() {
        GetBooksQuery getBooksQuery =
                new GetBooksQuery();

        List<BookRestModel> bookRestModels =
        queryGateway.query(getBooksQuery,
                ResponseTypes.multipleInstancesOf(BookRestModel.class))
                .join();

        return bookRestModels;
    }
}
