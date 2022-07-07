package com.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.entity.Book;
import com.library.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;
	
	public List<Book> getBooks() {
		return repository.findAll();
	}

	public void create(Book book) {
		repository.save(book);
	}

	public void update(Book book) {
		if (repository.existsById(book.getId())) {
			repository.save(book);
		}
	}

	public void delete(Book book) {
		repository.delete(book);
	}

}
