package com.tutorial.bookservice.commands.aggregate;

import com.tutorial.bookservice.commands.commands.CreateBookCommand;
import com.tutorial.bookservice.commands.events.BookCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class BookAggregate {

    @AggregateIdentifier
    private String bookId;
    private String title;
    private String author;
    private int studentId;


    @CommandHandler
    public BookAggregate(CreateBookCommand createBookCommand) {
        BookCreatedEvent bookCreatedEvent =
                new BookCreatedEvent();

        BeanUtils.copyProperties(createBookCommand,bookCreatedEvent);

        AggregateLifecycle.apply(bookCreatedEvent);
    }

    public BookAggregate() {
    }

    @EventSourcingHandler
    public void on(BookCreatedEvent bookCreatedEvent) {
        this.title = bookCreatedEvent.getTitle();
        this.bookId = bookCreatedEvent.getBookId();
        this.author = bookCreatedEvent.getAuthor();
        this.studentId = bookCreatedEvent.getStudentId();
    }
}
