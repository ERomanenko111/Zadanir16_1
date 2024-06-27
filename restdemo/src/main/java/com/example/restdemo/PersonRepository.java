package com.example.restdemo;

import com.example.restdemo.dto.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Autowired
private PersonRepository repository;