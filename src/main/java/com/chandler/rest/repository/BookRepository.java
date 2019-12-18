package com.chandler.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chandler.rest.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
}

