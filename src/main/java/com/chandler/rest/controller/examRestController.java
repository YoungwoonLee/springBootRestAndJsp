package com.chandler.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chandler.rest.domain.Book;
import com.chandler.rest.service.BookService;

@RestController
public class examRestController {
	@Autowired
	private BookService bookService;
	
	/**
	 * ��ü ����Ʈ�� ��´�
	 * @return
	 */
	@GetMapping("/api/books")
	public List<Book> getBookList() {
		return bookService.getBookList();
	}

	/**
	 * id�� ���� �Ѱ��� ������ ������ ��´�..
	 * @param id
	 * @return
	 */
	@GetMapping("/api/books/{id}")
	public Optional<Book> getDetail(@PathVariable Long id) {
		return bookService.getBookOne(id);
	}

	/**
	 * ��ü�� ���� �Ѵ�
	 * @param book
	 * @return
	 */
	@Transactional
	@PostMapping("/api/books")
	@ResponseStatus(HttpStatus.CREATED)
	public void addBook(@RequestBody Book book) {
		bookService.regBook(book);
	}

	/**
	 * ��ü�� �����Ѵ�.
	 * @param book
	 * @return
	 */
	@Transactional
	@PutMapping("/api/books/{id}")
	public Book modifyBook(@RequestBody Book book) {
		return bookService.modiBook(book);
	}

	/**
	 * ��ü�� �����Ѵ�
	 * @param id
	 * @return
	 */
	@Transactional
	@DeleteMapping("/api/books/{id}")
	public String deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return "SUCCESS";
	}

}