package com.tutorial.bookservice.commands.events;

import com.tutorial.bookservice.commands.data.Book;
import com.tutorial.bookservice.commands.data.BookRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("book")
public class BookEventsHandler {

    private BookRepository bookRepository;

    public BookEventsHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventHandler
    public void on(BookCreatedEvent event)  {
        Book book =
                new Book();
        BeanUtils.copyProperties(event,book);
        bookRepository.save(book);
    }

}
