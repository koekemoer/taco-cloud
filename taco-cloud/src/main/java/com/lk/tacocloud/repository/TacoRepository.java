package com.lk.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;

import com.lk.tacocloud.domain.Taco;

public interface TacoRepository 
         extends CrudRepository<Taco, Long> {

}