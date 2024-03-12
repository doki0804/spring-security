package com.example.springsecurity.security.repository;

import com.example.springsecurity.security.entity.Auth;
import com.example.springsecurity.security.entity.AuthId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRepository extends JpaRepository<Auth, AuthId>{

	@Query("SELECT a FROM Auth a JOIN FETCH a.menu m WHERE a.id.authCd = :authCd")
	List<Auth> findAllByIdAuthCdWithMenu(@Param("authCd") String authCd);

}
