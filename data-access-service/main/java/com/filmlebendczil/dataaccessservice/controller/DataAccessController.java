package com.filmlebendczil.dataaccessservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.ws.rs.PathParam;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	@Autowired(required = true)
	private MovieRatingRepository movieRatingRepo;

	@GetMapping("/search")
	public ResponseEntity<Object> search(@RequestParam(name = "searchPhrase") String phrase) {
		// String map =" [ { \"title\": \"Test\", \"description\": \"Test movie\",
		// \"director\": \"Nobody\", \"year\": 2020 }, { \"title\": \"Another test\",
		// \"description\": \"Another test movie. Electric boogaloo!\", \"director\":
		// \"Somebody\", \"year\": 2021 } ]";
		List<Movie> resultList = movieRepo.findByNameContaining(phrase);
		return ResponseEntity.ok(resultList);
	}

	@PostMapping("/add-movie")
	public void addMovie(@RequestBody Movie newMovie) {

		movieRepo.save(newMovie);
	}

	@GetMapping("/get-rating")
	public ResponseEntity<Object> getRating(@RequestParam(name = "movieId") int movieId) {
		MovieRating rating = movieRatingRepo.findByMovieId(movieId);
		return ResponseEntity.ok(rating);
	}

	@GetMapping("/get-user/{token}")
	public ResponseEntity<Member> getCertainUser(@PathVariable("token") String token) {

		Member member = memberRepo.findByToken(token);
		if(member == null) {
			return (ResponseEntity<Member>) ResponseEntity.notFound();
		}
		return ResponseEntity.ok(member);
	}
	//aktualizacja ratingu danego filmu i dodanie ratingu do bazy 
	@PutMapping("/update-rating")
	public void updateRating(@RequestBody MovieRating rating) {

		Long id = rating.getMovieId();
		Long userId = rating.getMemberId();
		
		
		movieRatingRepo.save(rating);

		Optional<Movie> movie = movieRepo.findById(id);
		Movie toUpdate = movie.get();

		List<MovieRating> allRatings = movieRatingRepo.findAllByMovieId(id);
		int counter = 0;
		double sum1 = 0.0;
		double sum2 = 0.0;
		double sum3 = 0.0;
		double sum4 = 0.0;
		double sum5 = 0.0;
		double sum6 = 0.0;

		for (MovieRating mr : allRatings) {
			counter++;
			sum1 += mr.getCat1();
			sum2 += mr.getCat2();
			sum3 += mr.getCat3();
			sum4 += mr.getCat4();
			sum5 += mr.getCat5();
			sum6 += mr.getCat6();

		}

		toUpdate.setRatings(counter, sum1, sum2, sum3, sum4, sum5, sum6);
		movieRepo.save(toUpdate);
		updateUserProfile(userId);
	}
	// aktualizacja profilu uzytkownika
	public void updateUserProfile(Long userId) {
		List<MovieRating> ratingByUser = movieRatingRepo.findAllByMemberId(userId);
		Optional<Member> member = memberRepo.findById(userId);
		Member updatable = member.get();
		double weightSum = 0.0;
		double sum1 = 0.0;
		double sum2 = 0.0;
		double sum3 = 0.0;
		double sum4 = 0.0;
		double sum5 = 0.0;
		double sum6 = 0.0;
		for(MovieRating mr : ratingByUser) {
			double score = mr.getScore();
			
			if(score> 0.5) {
				weightSum += score;
				sum1 += mr.getCat1() * score;
				sum2 += mr.getCat2() * score;
				sum3 += mr.getCat3() * score;
				sum4 += mr.getCat4() * score;
				sum5 += mr.getCat5() * score;
				sum6 += mr.getCat6() * score;

			}
			
		}
		updatable.setCat1(sum1/weightSum);
		updatable.setCat2(sum2/weightSum);
		updatable.setCat3(sum3/weightSum);
		updatable.setCat4(sum4/weightSum);
		updatable.setCat5(sum5/weightSum);
		updatable.setCat6(sum6/weightSum);
		memberRepo.save(updatable);
	}

	@PostMapping("/create-account")
	public ResponseEntity<Object> addUserToDatabase(@RequestBody Member newMember) {

		memberRepo.save(newMember);
		return ResponseEntity.ok(newMember);

	}

	@GetMapping("/check-recommendation")
	public ResponseEntity<Object> checkRecommendations(
			@RequestBody List<Double> multipliers, @RequestBody Long id, @RequestBody int amountOfMovies ) {
	
		
		Optional<Member> yikes = memberRepo.findById(id);
		Member member = yikes.get();
		List<Movie> availableMovies = movieRepo.findAll();
		HashMap<Long, Double> map = new HashMap<Long, Double>();
		for(Movie m : availableMovies) {
			double d = calculateDifference(m, member, multipliers);
			map.put(m.getID(), d);
		}
		HashMap<Long, Double> resulting = 
				map.entrySet()					
				.stream()							
				.sorted(Entry.comparingByValue())
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue,
		(e1, e2) -> e1, LinkedHashMap<Long, Double>::new));
		
		
		return ResponseEntity.ok(resulting);
	}
	
	
	
	//obliczanie wskaznika
	public double calculateDifference(Movie movie, Member member, List<Double> multipliers) {
		double d = 0.0;
		
		d = 	Math.abs(movie.getCat1() - member.getCat1()) * multipliers.get(0) +
				Math.abs(movie.getCat2() - member.getCat2()) * multipliers.get(1) +
				Math.abs(movie.getCat3() - member.getCat3()) * multipliers.get(2) +
				Math.abs(movie.getCat4() - member.getCat4()) * multipliers.get(3) +
				Math.abs(movie.getCat5() - member.getCat5()) * multipliers.get(4) +
				Math.abs(movie.getCat6() - member.getCat6()) * multipliers.get(5);
		return d;
	}
}
	

