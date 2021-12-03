package com.filmlebendczil.dataaccessservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filmlebendczil.dataaccessservice.entity.Member;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{

}
