package com.filmlebendczil.dataaccessservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.h2.util.json.JSONObject;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.filmlebendczil.dataaccessservice.entity.Member;

import com.filmlebendczil.dataaccessservice.repository.MemberRepository;

@RestController
@CrossOrigin
public class DataAccessController {
	@Autowired(required = true)
	private MemberRepository userRepo;
	private SessionFactory sf = new Configuration().configure().buildSessionFactory();

	@GetMapping("/search")
	public ResponseEntity<Object> search(@RequestParam(name = "searchPhrase") String phrase) {
	String map =" [ { \"title\": \"Test\", \"description\": \"Test movie\", \"director\": \"Nobody\", \"year\": 2020 }, { \"title\": \"Another test\", \"description\": \"Another test movie. Electric boogaloo!\", \"director\": \"Somebody\", \"year\": 2021 } ]";
		
		return ResponseEntity.ok(map);
	}

	@GetMapping("/create-account")
	@Modifying
	@Query(value = "insert into MEMBER  (token, username) values (:token, :username)", nativeQuery = true)
	public String addUserToDatabase(@RequestParam(name = "token") String token,
			@RequestParam(name = "username") String username) {
		Session session = sf.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Member member = new Member(token, username);

			session.save(member);
			transaction.commit();

		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
				e.printStackTrace();
			}
		} finally {
			session.close();

		}
		return "Added user with token: " + token + " and username: " + username;

	}

}
