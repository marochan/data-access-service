package com.filmlebendczil.dataaccessservice.entity;

import java.sql.Date;

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

	@Column(name = "movie_id")
	private Long movieId;

	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "creation_date")
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
	
	@Column(name = "score")
	private double score;

	public MovieRating() {
		super();
	}

	public MovieRating(Long movieId, Long memberId, Date createDate, double cat1, double cat2, double cat3, double cat4,
			double cat5, double cat6, double score) {
		super();
		this.movieId = movieId;
		this.memberId = memberId;
		this.createDate = createDate;
		this.cat1 = cat1;
		this.cat2 = cat2;
		this.cat3 = cat3;
		this.cat4 = cat4;
		this.cat5 = cat5;
		this.cat6 = cat6;
		this.score = score;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	

}
