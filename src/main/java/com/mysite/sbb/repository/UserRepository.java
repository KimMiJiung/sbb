package com.mysite.sbb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.sbb.entity.SiteUser;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long> {
	
	Optional<SiteUser> findByUsername(String username);
}
