package com.filmlebendczil.dataaccessservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MovieRating {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "movie")
	private String movieName;

	@Column(name = "username")
	private String username;

	@Column(name = "cat1")
	private int cat1;

	@Column(name = "cat2")
	private int cat2;

	@Column(name = "cat3")
	private int cat3;

	@Column(name = "cat4")
	private int cat4;

	@Column(name = "cat5")
	private int cat5;

	@Column(name = "cat6")
	private int cat6;

	public MovieRating() {
		super();
	}

	public MovieRating( String movieName, String username, int cat1, int cat2, int cat3, int cat4, int cat5,
			int cat6) {
		super();
	
		this.movieName = movieName;
		this.username = username;
		this.cat1 = cat1;
		this.cat2 = cat2;
		this.cat3 = cat3;
		this.cat4 = cat4;
		this.cat5 = cat5;
		this.cat6 = cat6;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCat1() {
		return cat1;
	}

	public void setCat1(int cat1) {
		this.cat1 = cat1;
	}

	public int getCat2() {
		return cat2;
	}

	public void setCat2(int cat2) {
		this.cat2 = cat2;
	}

	public int getCat3() {
		return cat3;
	}

	public void setCat3(int cat3) {
		this.cat3 = cat3;
	}

	public int getCat4() {
		return cat4;
	}

	public void setCat4(int cat4) {
		this.cat4 = cat4;
	}

	public int getCat5() {
		return cat5;
	}

	public void setCat5(int cat5) {
		this.cat5 = cat5;
	}

	public int getCat6() {
		return cat6;
	}

	public void setCat6(int cat6) {
		this.cat6 = cat6;
	}

}
