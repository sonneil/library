package com.library.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Reader {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String phone;
	private String address;
	@OneToMany
	private List<Borrow> borrows;
	
	private Date startPenalty;
	private Date endPenalty;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Borrow> getBorrows() {
		return borrows;
	}
	public void setBorrows(List<Borrow> borrows) {
		this.borrows = borrows;
	}
	public Date getStartPenalty() {
		return startPenalty;
	}
	public void setStartPenalty(Date startPenalty) {
		this.startPenalty = startPenalty;
	}
	public Date getEndPenalty() {
		return endPenalty;
	}
	public void setEndPenalty(Date endPenalty) {
		this.endPenalty = endPenalty;
	}
}
