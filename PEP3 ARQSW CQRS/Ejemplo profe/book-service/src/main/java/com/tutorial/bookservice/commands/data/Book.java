package com.tutorial.bookservice.commands.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Book {

    @Id
    private String bookId;
    private String title;
    private String author;
    private int studentId;
}
