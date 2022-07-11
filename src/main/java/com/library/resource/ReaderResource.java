package com.library.resource;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.entity.Book;
import com.library.entity.Copy;
import com.library.entity.Reader;
import com.library.service.BookService;
import com.library.service.ReaderService;

@RestController
@RequestMapping(value = "/reader")
public class ReaderResource {
	
	@Autowired
	private ReaderService service;
	
	@PostMapping(value = "/{readerId}/copy", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> borrow(@PathVariable("readerId") Long readerId, @RequestBody Copy copy) {
		LocalDate today = LocalDate.now();
		service.borrow(readerId, copy, today);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> create(@RequestBody Reader reader) {
		service.create(reader);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public List<Reader> findAll() {
		return service.findAll();
	}
	
	
}
