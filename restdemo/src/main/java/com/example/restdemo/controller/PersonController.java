package com.example.restdemo.controller;

import com.example.restdemo.Service.PersonService;
import com.example.restdemo.dto.Message;
import com.example.restdemo.dto.Person;
import com.example.restdemo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return new ResponseEntity<>(service.addMessageToPerson(id, message), HttpStatus.OK);
    }

    @DeleteMapping("/{personId}/message/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int personId, @PathVariable int messageId) {
        service.deleteMessage(personId, messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<List<Message>> getMessagesByPersonId(@PathVariable int id) {
        return new ResponseEntity<>(service.getMessagesByPersonId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {
        if (repository.existsById(id)) {
            person.setId(id);
            repository.save(person);
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}