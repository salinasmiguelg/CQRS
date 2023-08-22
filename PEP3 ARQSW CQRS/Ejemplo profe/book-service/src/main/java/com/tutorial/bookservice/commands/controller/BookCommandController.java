package com.tutorial.bookservice.commands.controller;

import com.tutorial.bookservice.commands.commands.CreateBookCommand;
import com.tutorial.bookservice.commands.model.BookRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookCommandController {

    private CommandGateway commandGateway;

    public BookCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String addBook(@RequestBody BookRestModel bookRestModel) {

        CreateBookCommand createBookCommand =
                CreateBookCommand.builder()
                        .bookId(UUID.randomUUID().toString())
                        .title(bookRestModel.getTitle())
                        .author(bookRestModel.getAuthor())
                        .studentId(bookRestModel.getStudentId())
                        .build();
        String result = commandGateway.sendAndWait(createBookCommand);
        return result;
    }
}
