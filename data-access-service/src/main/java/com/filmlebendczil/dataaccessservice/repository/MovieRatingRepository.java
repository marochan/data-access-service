package com.filmlebendczil.dataaccessservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filmlebendczil.dataaccessservice.entity.MovieRating;

@Repository
public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {
	
	MovieRating findByMovieId(long movieId);
	List<MovieRating> findAllByMovieId(Long movieId);
	List<MovieRating> findAllByMemberId(Long memberId);
}


