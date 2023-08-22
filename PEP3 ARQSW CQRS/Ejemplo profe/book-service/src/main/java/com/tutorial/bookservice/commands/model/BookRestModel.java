package com.tutorial.bookservice.commands.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRestModel {
    private String title;
    private String author;
    private int studentId;
}
