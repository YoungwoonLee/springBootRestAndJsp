package com.chandler.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chandler.rest.domain.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
	
}

