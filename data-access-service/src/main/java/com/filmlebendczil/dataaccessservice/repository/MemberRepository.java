package com.filmlebendczil.dataaccessservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filmlebendczil.dataaccessservice.entity.Member;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
		Member findByToken(String token);
		Optional<Member> findById(Long id);
}
