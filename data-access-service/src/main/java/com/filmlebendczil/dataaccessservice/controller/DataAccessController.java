package com.filmlebendczil.dataaccessservice.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

		List<Movie> resultList = movieRepo.findByNameContaining(phrase);
		return ResponseEntity.ok(resultList);
	}

	@PostMapping("/add-movie")
	public ResponseEntity<Object> addMovie(@RequestBody Movie newMovie) {
		if (movieRepo.findByName(newMovie.getName()) != null) {

			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Movie already exists in the database");
		}
		movieRepo.save(newMovie);
		return ResponseEntity.ok(newMovie);
	}

	@GetMapping("/get-rating")
	public ResponseEntity<Object> getRating(@RequestParam(name = "movieId") Long movieId) {
		Movie movie = movieRepo.findById(movieId).get();
		if(movieRatingRepo.findAllByMovieId(movieId)==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie has not been reviewed yet!");
		}
	
		return ResponseEntity.ok(movie);
	}

	@GetMapping("/get-user/{userId}")
	public ResponseEntity<Object> getCertainUser(@PathVariable("userId") String userId) {
		Member member = memberRepo.findByUserId(userId);
		if(memberRepo.findByUserId(userId)==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with specified name: " + userId + " is not registered in the system");
		}
		
		
		return ResponseEntity.ok(member);
	}

	// aktualizacja ratingu danego filmu i dodanie ratingu do bazy
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
		for (MovieRating mr : ratingByUser) {
			double score = mr.getScore();

			if (score > 0.5) {
				weightSum += score;
				sum1 += mr.getCat1() * score;
				sum2 += mr.getCat2() * score;
				sum3 += mr.getCat3() * score;
				sum4 += mr.getCat4() * score;
				sum5 += mr.getCat5() * score;
				sum6 += mr.getCat6() * score;

			}

		}
		updatable.setCat1(sum1 / weightSum);
		updatable.setCat2(sum2 / weightSum);
		updatable.setCat3(sum3 / weightSum);
		updatable.setCat4(sum4 / weightSum);
		updatable.setCat5(sum5 / weightSum);
		updatable.setCat6(sum6 / weightSum);
		memberRepo.save(updatable);
	}

	@PostMapping("/create-account")
	public ResponseEntity<Object> addUserToDatabase(@RequestBody Member newMember) {

		if (memberRepo.findByUserId(newMember.getUserId()) != null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User already exists in the database");
		}
		memberRepo.save(newMember);
		return ResponseEntity.ok(newMember);

	}

	@GetMapping("/check-recommendation")
	public ResponseEntity<Object> checkRecommendations(@RequestParam(name = "id") Long id,
			@RequestParam(name = "amountOfMovies") int amountOfMovies,
			@RequestParam(name = "multipliers") String multipliers)
			throws JsonMappingException, JsonParseException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Double> multipliersMap = mapper.readValue(multipliers, Map.class);
		System.out.println(multipliersMap.toString());
		Optional<Member> yikes = memberRepo.findById(id);
		Member member = yikes.get();
		List<Movie> availableMovies = movieRepo.findAll();
		HashMap<Long, Double> map = new HashMap<Long, Double>();
		for (Movie m : availableMovies) {
			double d = calculateDifference(m, member, multipliersMap);
			map.put(m.getID(), d);
		}
		HashMap<Long, Double> sorted = map.entrySet().stream().sorted(Entry.comparingByValue()).limit(amountOfMovies)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1,
						LinkedHashMap<Long, Double>::new));

		List<Movie> resulting = new ArrayList<Movie>();
		for (Map.Entry<Long, Double> entry : sorted.entrySet()) {
			resulting.add(movieRepo.findById(entry.getKey()).get());
		}

		return ResponseEntity.ok(resulting);
	}

	// obliczanie wskaznika
	public double calculateDifference(Movie movie, Member member, Map<String, Double> multipliers) {
		double d = 0.0;

		d = Math.abs(movie.getCat1() - member.getCat1()) * multipliers.get("m1")
				+ Math.abs(movie.getCat2() - member.getCat2()) * multipliers.get("m2")
				+ Math.abs(movie.getCat3() - member.getCat3()) * multipliers.get("m3")
				+ Math.abs(movie.getCat4() - member.getCat4()) * multipliers.get("m4")
				+ Math.abs(movie.getCat5() - member.getCat5()) * multipliers.get("m5")
				+ Math.abs(movie.getCat6() - member.getCat6()) * multipliers.get("m6");
		return d;
	}
}
