package com.library.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.entity.Book;
import com.library.service.BookService;

@RestController
@RequestMapping(value = "/book")
public class BookResource {
	
	@Autowired
	private BookService service;
	
	@GetMapping
	public List<Book> get() {
		return service.getBooks();
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> post(@RequestBody Book book) {
		service.create(book);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> put(@RequestBody Book book) {
		service.update(book);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> delete(@RequestBody Book book) {
		service.delete(book);
		return ResponseEntity.ok().build();
	}
	
	
}
