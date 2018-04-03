package com.filmbaaz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filmbaaz.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
