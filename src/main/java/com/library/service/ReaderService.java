package com.library.service;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.entity.Borrow;
import com.library.entity.Copy;
import com.library.entity.Reader;
import com.library.repository.BorrowRepository;
import com.library.repository.CopyRepository;
import com.library.repository.ReaderRepository;

@Service
public class ReaderService {
	
	final Long IN_LIBRARY_STATUS = 1L;
	final Long BORROWED_STATUS = 2L;
	final Long PENALTY_STATUS = 3L;
	final Long REPAIR_STATUS = 4L;

	@Autowired
	private BorrowRepository borrowRepository;
	@Autowired
	private ReaderRepository readerRepository;
	@Autowired
	private CopyRepository copyRepository;
	
	public void borrow(Long readerId, Copy copy, LocalDate today) {
		Copy c = copyRepository.findById(copy.getId()).get();
		if (Objects.isNull(c) || c.getStatus() != IN_LIBRARY_STATUS) return;
		
		Reader reader = readerRepository.findById(readerId).get();
		multar(reader, today);
		
		Integer borrowsAmount = reader.getBorrows().size();
		Boolean hasPenalty = hasPenalty(reader, today);
		
		if (borrowsAmount >= 3) return;
		if (hasPenalty) return;
		
		Borrow borrow = new Borrow();
		copy.setStatus(BORROWED_STATUS);
		borrow.setStartDate(Date.valueOf(today));
		borrow.setCopy(copyRepository.save(copy));
		reader.getBorrows().add(borrowRepository.save(borrow));
		readerRepository.save(reader);
	}

	private void multar(Reader reader, LocalDate today) {
		Long daysExceeded = exceededDays(reader);
		
		if (daysExceeded > 0) {
			Date startPenaltyDate = hasPenalty(reader, today) ? reader.getStartPenalty() : Date.valueOf(today);
			Date endPenaltyDate = calculateEndPenaltyDate(startPenaltyDate, daysExceeded);
			reader.setStartPenalty(startPenaltyDate);
			reader.setEndPenalty(endPenaltyDate);
			readerRepository.save(reader);
		}
	}
	
	private Date calculateEndPenaltyDate(Date startPenaltyDate, Long daysExceeded) {
		LocalDate startPenaltyLocalDate = startPenaltyDate.toLocalDate();
		Date endPenaltyDate = Date.valueOf(startPenaltyLocalDate.plusDays(daysExceeded*2));
		return endPenaltyDate;
	}

	private Boolean hasPenalty(Reader reader, LocalDate today) {
		if (Objects.isNull(reader.getStartPenalty()) || Objects.isNull(reader.getEndPenalty())) {
			return Boolean.FALSE;
		}
		
		LocalDate startPenaltyDate = reader.getStartPenalty().toLocalDate();
		LocalDate endPenaltyDate = reader.getEndPenalty().toLocalDate();
		
		return today.isAfter(startPenaltyDate.minusDays(1l)) && today.isBefore(endPenaltyDate);
	}
	
	private Long exceededDays(Reader reader) {
		Long daysExceeded = 0l;
		LocalDate today = LocalDate.now();
		List<Borrow> borrows = reader.getBorrows() != null ? reader.getBorrows() : new ArrayList<>();
		for (Borrow b : borrows) {
			if (!Objects.isNull(b.getCopy()) && b.getCopy().getStatus() == BORROWED_STATUS) {
				LocalDate startBorrowDate = b.getStartDate().toLocalDate();
				Long days = Duration.between(startBorrowDate.atStartOfDay(), today.atStartOfDay()).toDays();
				if (days > 30) {
					daysExceeded += (days - 30);
					
					Copy copy = b.getCopy();
					copy.setStatus(PENALTY_STATUS);
					copyRepository.save(copy);
				}
			}
		}
		return daysExceeded;
	}

	public void create(Reader reader) {
		readerRepository.save(reader);
	}

	public List<Reader> findAll() {
		return readerRepository.findAll();
	}

	public void borrowBack(Long readerId, Copy copy, LocalDate today) {
		Copy c = copyRepository.findById(copy.getId()).get();
		
		if (Objects.isNull(c) || !Arrays.asList(BORROWED_STATUS, PENALTY_STATUS, IN_LIBRARY_STATUS).contains(c.getStatus())) {
			return;
		}
		
		Reader reader = readerRepository.findById(readerId).get();
		multar(reader, today);
		
		Borrow borrow = copyRepository.findBorrowByCopyId(copy.getId());
		copy.setStatus(IN_LIBRARY_STATUS);
		borrow.setStartDate(null);
		borrow.setCopy(copyRepository.save(copy));
		reader.setBorrows(removeFromList(reader.getBorrows(), borrow));
		borrowRepository.delete(borrow);
		readerRepository.save(reader);	
	}

	private List<Borrow> removeFromList(List<Borrow> borrows, Borrow borrow) {
		if (borrows == null) return new ArrayList<>();
		
		List<Borrow> newBorrows = new ArrayList<>();
		for (Borrow b : borrows) {
			if (!b.getId().equals(borrow.getId())) {
				newBorrows.add(borrow);
			}
		}
		return newBorrows;
	}

}
