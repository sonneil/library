package com.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.entity.Copy;
import com.library.repository.CopyRepository;

@Service
public class CopyService {

	@Autowired
	private CopyRepository repository;
	
	public List<Copy> getCopys() {
		return repository.findAll();
	}

	public void create(Copy Copy) {
		repository.save(Copy);
	}

	public void update(Copy Copy) {
		if (repository.existsById(Copy.getId())) {
			repository.save(Copy);
		}
	}

	public void delete(Copy Copy) {
		repository.delete(Copy);
	}

}
