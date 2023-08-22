package com.tutorial.bookservice.commands.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateBookCommand {

    @TargetAggregateIdentifier
    private String bookId;
    private String title;
    private String author;
    private int studentId;

}
