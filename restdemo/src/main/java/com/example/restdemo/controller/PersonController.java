package com.example.restdemo.controller;

import com.example.restdemo.dto.Person;
import com.example.restdemo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Person")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @PostMapping("")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        Person savedPerson = repository.save(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person updatedPerson) {
        Person existingPerson = repository.findById(id).orElse(null);
        if (existingPerson != null) {
            existingPerson.setFirstname(updatedPerson.getFirstname());
            existingPerson.setSurname(updatedPerson.getSurname());
            existingPerson.setLastname(updatedPerson.getLastname());
            existingPerson.setBirthday(updatedPerson.getBirthday());
            repository.save(existingPerson);
            return new ResponseEntity<>(existingPerson, HttpStatus.OK);
        } else {
            updatedPerson.setId(id);
            repository.save(updatedPerson);
            return new ResponseEntity<>(updatedPerson, HttpStatus.CREATED);
        }
    }

    @GetMapping("/")
    public Iterable<Person> getPersons() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findPersonById(@PathVariable int id) {
        return repository.findById(id)
                .map(person -> ResponseEntity.ok().body(person))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}