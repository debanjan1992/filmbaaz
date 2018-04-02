package com.filmbaaz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filmbaaz.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
