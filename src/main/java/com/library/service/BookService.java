package com.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.entity.Book;
import com.library.entity.Copy;
import com.library.repository.BookRepository;
import com.library.repository.CopyRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;
	@Autowired
	private CopyRepository copyRepository;
	
	public List<Book> getBooks() {
		return repository.findAll();
	}

	public void create(Book book) {
		/*for (Copy c : book.getCopies()) {
			c.setId(copyRepository.save(c).getId());
		}*/
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
