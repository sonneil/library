package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.entity.Book;
import com.library.entity.Borrow;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {}