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

import com.library.entity.Copy;
import com.library.service.CopyService;

@RestController
@RequestMapping(value = "/copy")
public class CopyResource {
	
	@Autowired
	private CopyService service;
	
	@GetMapping
	public List<Copy> get() {
		return service.getCopys();
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Copy> post(@RequestBody Copy Copy) {
		service.create(Copy);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Copy> put(@RequestBody Copy Copy) {
		service.update(Copy);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Copy> delete(@RequestBody Copy Copy) {
		service.delete(Copy);
		return ResponseEntity.ok().build();
	}
	
	
}
