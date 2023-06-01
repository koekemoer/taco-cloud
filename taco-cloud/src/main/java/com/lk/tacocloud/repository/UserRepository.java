package com.lk.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;

import com.lk.tacocloud.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    
}
