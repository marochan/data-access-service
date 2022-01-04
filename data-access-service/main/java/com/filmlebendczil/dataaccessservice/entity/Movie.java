package com.filmlebendczil.dataaccessservice.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MOVIE")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long ID;

	@Column(name = "name")
	private String name;

	@Column(name = "production_year")
	private Integer year;

	@Column(name = "creation_date", columnDefinition = "DATE")
	private Date createDate;

	@Column(name = "cat1")
	private double cat1;

	@Column(name = "cat2")
	private double cat2;

	@Column(name = "cat3")
	private double cat3;

	@Column(name = "cat4")
	private double cat4;

	@Column(name = "cat5")
	private double cat5;

	@Column(name = "cat6")
	private double cat6;
	
	public void setRatings(int counter, double cat1, double cat2, double cat3, double cat4, double cat5, double cat6) {
		this.cat1 = cat1/counter;
		this.cat2 = cat2/counter;
		this.cat3 = cat3/counter;
		this.cat4 = cat4/counter;
		this.cat5 = cat5/counter;
		this.cat6 = cat6/counter;
	}

	public Movie() {
		super();
	}

	public Movie(String name, Integer year, Date createDate, double cat1, double cat2, double cat3, double cat4,
			double cat5, double cat6) {
		super();
		this.name = name;
		this.year = year;
		this.createDate = createDate;
		this.cat1 = cat1;
		this.cat2 = cat2;
		this.cat3 = cat3;
		this.cat4 = cat4;
		this.cat5 = cat5;
		this.cat6 = cat6;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public double getCat1() {
		return cat1;
	}

	public void setCat1(double cat1) {
		this.cat1 = cat1;
	}

	public double getCat2() {
		return cat2;
	}

	public void setCat2(double cat2) {
		this.cat2 = cat2;
	}

	public double getCat3() {
		return cat3;
	}

	public void setCat3(double cat3) {
		this.cat3 = cat3;
	}

	public double getCat4() {
		return cat4;
	}

	public void setCat4(double cat4) {
		this.cat4 = cat4;
	}

	public double getCat5() {
		return cat5;
	}

	public void setCat5(double cat5) {
		this.cat5 = cat5;
	}

	public double getCat6() {
		return cat6;
	}

	public void setCat6(double cat6) {
		this.cat6 = cat6;
	}

	

}
