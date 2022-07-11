package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.entity.Book;
import com.library.entity.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {}