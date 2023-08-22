package com.tutorial.bookservice.commands.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,String> {
}
