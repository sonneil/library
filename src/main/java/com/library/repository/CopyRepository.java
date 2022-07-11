package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.entity.Borrow;
import com.library.entity.Copy;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {

	@Query("SELECT b FROM Borrow b WHERE b.copy.id = :id")
	Borrow findBorrowByCopyId(@Param("id") Long id);
}