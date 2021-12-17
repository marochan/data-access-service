package com.filmlebendczil.dataaccessservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long ID;
	@Column(name = "token")
	private String token;
	@Column(name = "username")
	private String username;

	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Member(String token, String username) {
		super();
		this.token = token;
		this.username = username;
	}
	
	
	public Long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
