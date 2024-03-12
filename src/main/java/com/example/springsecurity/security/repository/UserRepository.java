package com.example.springsecurity.security.repository;

import java.util.List;
import java.util.Optional;

import com.example.springsecurity.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findByUserId(String userId);	//회원 정보 가져오기


	boolean existsByUserId(String userId);
}