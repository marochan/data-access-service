package com.filmlebendczil.dataaccessservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.filmlebendczil.dataaccessservice.entity.Member;
import com.filmlebendczil.dataaccessservice.entity.Movie;
import com.filmlebendczil.dataaccessservice.entity.MovieRating;
import com.filmlebendczil.dataaccessservice.repository.MemberRepository;
import com.filmlebendczil.dataaccessservice.repository.MovieRatingRepository;
import com.filmlebendczil.dataaccessservice.repository.MovieRepository;

@RestController
@CrossOrigin
public class DataAccessController {
	@Autowired(required = true)
	private MemberRepository memberRepo;
	@Autowired(required = true)
	private MovieRepository movieRepo;
	@Autowired(required=true)
	private MovieRatingRepository movieRatingRepo;
	

	
	
	
	@GetMapping("/search")
	public ResponseEntity<Object> search(@RequestParam(name = "searchPhrase") String phrase) {
	//String map =" [ { \"title\": \"Test\", \"description\": \"Test movie\", \"director\": \"Nobody\", \"year\": 2020 }, { \"title\": \"Another test\", \"description\": \"Another test movie. Electric boogaloo!\", \"director\": \"Somebody\", \"year\": 2021 } ]";
		List<Movie> resultList = movieRepo.findByNameContaining(phrase);
		return ResponseEntity.ok(resultList);
	}
	
	
	
	
	@GetMapping("/add-movie")
	public void addMovie(@RequestParam(name = "name") String name,@RequestParam(name="year") int year) {
		Movie movie = new Movie(name, year);
		movieRepo.save(movie);
		
		
	}
	
	
	
	
	
	
	@GetMapping("/get-rating")
	public ResponseEntity<Object> getRating(@RequestParam(name="movieName")String movieName){
		MovieRating rating = movieRatingRepo.findByMovieName(movieName);
		return ResponseEntity.ok(rating);
	}
	
	
	
	@GetMapping("/update-rating")
	public void updateRating(@RequestParam(name="token") String token,
							 @RequestParam(name="movie") String movie,
							 @RequestParam(name="cat1") int cat1,
							 @RequestParam(name="cat2") int cat2,
							 @RequestParam(name="cat3") int cat3,
							 @RequestParam(name="cat4") int cat4,
							 @RequestParam(name="cat5") int cat5,
							 @RequestParam(name="cat6") int cat6
			) {
		Movie theOneToUpdate = movieRepo.findByName(movie);
		Long movieToUpdate = theOneToUpdate.getID();
		MovieRating mr = movieRatingRepo.getById(movieToUpdate);
		mr.setCat1(cat1);
		mr.setCat2(cat2);
		mr.setCat3(cat3);
		mr.setCat4(cat4);
		mr.setCat5(cat5);
		mr.setCat6(cat6);
		movieRatingRepo.save(mr);
		
	}
	
	
	
	
	@GetMapping("/create-account")
	public String addUserToDatabase(@RequestParam(name = "token") String token,
			@RequestParam(name = "username") String username) {
	
		
		Member member = new Member(token, username);

		memberRepo.save(member);
		
	
		return "Added user with token: " + token + " and username: " + username;

	}
	
	@GetMapping("/check-reccomendation")
	public ResponseEntity<Object> checkRecommendations(@RequestParam(name = "username") String token){
		return null;
	}
}
