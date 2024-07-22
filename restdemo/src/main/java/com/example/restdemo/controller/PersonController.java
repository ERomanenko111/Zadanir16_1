package com.example.restdemo.controller;

import com.example.restdemo.Service.PersonService;
import com.example.restdemo.dto.Message;
import com.example.restdemo.dto.Person;
import com.example.restdemo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        if (repository.existsById(person.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        repository.save(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/message")
    public ResponseEntity<Person> addMessage(@PathVariable int id, @RequestBody Message message) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.addMeesageToPerson(id, message), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {
        HttpStatus status = repository.existsById(id) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(repository.save(person), status);
    }

    @GetMapping
    public Iterable<Person> getPersons() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findPersonById(@PathVariable int id) {
        Optional<Person> person = repository.findById(id);
        return person.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable int id) {
        repository.deleteById(id);
    }

    @GetMapping("/{id}/message")
    public ResponseEntity<List<Message>> getMessagesByPersonId(@PathVariable int id) {
        Optional<Person> person = repository.findById(id);
        return person.map(value -> new ResponseEntity<>(value.getMessages(), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}/message/{messageId}")
    public void deleteMessageFromPerson(@PathVariable int id, @PathVariable int messageId) {
        Optional<Person> person = repository.findById(id);
        person.ifPresent(value -> {
            List<Message> messages = value.getMessages();
            messages.removeIf(message -> message.getId() == messageId);
            repository.save(value);
        });
    }

    @GetMapping("/{id}/messageList")
    public List<Message> getMessageListByPersonId(@PathVariable int id) {
        Optional<Person> person = repository.findById(id);
        return person.map(Person::getMessages).orElseGet(Collections::emptyList);
    }
}