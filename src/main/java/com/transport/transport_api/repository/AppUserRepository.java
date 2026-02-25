package com.transport.transport_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.transport_api.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

  Optional<AppUser> findByUsername(String username);
}
