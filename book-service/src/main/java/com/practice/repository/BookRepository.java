package com.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{}
