package com.library.service;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.entity.Borrow;
import com.library.entity.Copy;
import com.library.entity.Reader;
import com.library.repository.BorrowRepository;
import com.library.repository.ReaderRepository;

@Service
public class ReaderService {
	
	final Long BORROWED_STATUS = 2l;

	@Autowired
	private BorrowRepository borrowRepository;
	@Autowired
	private ReaderRepository readerRepository;
	
	public void borrow(Long readerId, Copy copy, LocalDate today) {
		Reader reader = readerRepository.findById(readerId).get();
		multar(reader, today);
		
		Integer borrowsAmount = reader.getBorrows().size();
		Boolean hasPenalty = hasPenalty(reader, today);
		
		if (borrowsAmount >= 3) return;
		if (hasPenalty) return;
		
		Borrow borrow = new Borrow();
		borrow.setStartDate(Date.valueOf(today));
		borrow.setCopy(copy);
		borrow.setReader(reader);
		
		borrow = borrowRepository.save(borrow);
		reader.getBorrows().add(borrow);
		copy.setBorrow(borrow);
		readerRepository.save(reader);
		//copyRepository.save(copy);
	}

	private void multar(Reader reader, LocalDate today) {
		Long daysExceeded = exceededDays(reader);
		
		if (daysExceeded > 0) {
			Date startPenaltyDate = reader.getStartPenalty() != null ? reader.getStartPenalty() : Date.valueOf(today);
			Date endPenaltyDate = calculateEndPenaltyDate(startPenaltyDate, daysExceeded);
			reader.setStartPenalty(startPenaltyDate);
			reader.setEndPenalty(endPenaltyDate);
			readerRepository.save(reader);
		} else {
			reader.setStartPenalty(null);
			reader.setEndPenalty(null);
			readerRepository.save(reader);
		}
	}
	
	private Date calculateEndPenaltyDate(Date startPenaltyDate, Long daysExceeded) {
		LocalDate startPenaltyLocalDate = startPenaltyDate.toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
		Date endPenaltyDate = Date.valueOf(startPenaltyLocalDate.plusDays(daysExceeded*2));
		return endPenaltyDate;
	}

	private Boolean hasPenalty(Reader reader, LocalDate today) {
		if (Objects.isNull(reader.getStartPenalty()) || Objects.isNull(reader.getEndPenalty())) {
			return Boolean.FALSE;
		}
		
		LocalDate startPenaltyDate = reader.getStartPenalty().toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
		LocalDate endPenaltyDate = reader.getEndPenalty().toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
		
		return today.isAfter(startPenaltyDate.minusDays(1l)) && today.isBefore(endPenaltyDate);
	}
	
	private Long exceededDays(Reader reader) {
		Long daysExceeded = 0l;
		LocalDate today = LocalDate.now();
		List<Borrow> borrows = reader.getBorrows() != null ? reader.getBorrows() : new ArrayList<>();
		for (Borrow b : borrows) {
			if (b.getCopy().getStatus() == BORROWED_STATUS) {
				LocalDate startBorrowDate = b.getStartDate().toInstant()
					      .atZone(ZoneId.systemDefault())
					      .toLocalDate();
				Long days = Duration.between(startBorrowDate.atStartOfDay(), today.atStartOfDay()).toDays();
				if (days > 30) {
					daysExceeded += (days - 30);
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

}
