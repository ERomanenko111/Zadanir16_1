package com.example.restdemo.controller;

import com.example.restdemo.Service.PersonService;
import com.example.restdemo.dto.Message;
import com.example.restdemo.dto.Person;
import com.example.restdemo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {


    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonService service;

    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        repository.save(person);
        return person;
    }

    @PostMapping("/{id}/message")
    public Person addMessage(@PathVariable int id, @RequestBody Message message) {
        return service.addMeesageToPerson(id, message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {
        HttpStatus status = repository.existsById(id) ? HttpStatus.OK : HttpStatus.CREATED;
        return new ResponseEntity<>(repository.save(person), status);
    }

    @GetMapping
    public Iterable<Person> getPersons() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Person> findPersonById(@PathVariable int id) {
        return repository.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable int id) {
        repository.deleteById(id);
    }

    @GetMapping("/{id}/message")
    public ResponseEntity<List<Message>> getMessagesByPersonId(@PathVariable int id) {
        Optional<Person> person = repository.findById(id);
        if (person.isPresent()) {
            return new ResponseEntity<>(person.get().getMessages(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}