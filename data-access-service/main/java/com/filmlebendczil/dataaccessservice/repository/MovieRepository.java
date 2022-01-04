package com.filmlebendczil.dataaccessservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filmlebendczil.dataaccessservice.entity.Movie;
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{
	Movie findByName(String name);
	List<Movie> findByNameContaining(String name);
	Movie findById(long Id);
}
