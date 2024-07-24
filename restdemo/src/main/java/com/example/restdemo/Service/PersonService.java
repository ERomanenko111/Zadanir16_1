package com.example.restdemo.Service;

import com.example.restdemo.dto.Message;
import com.example.restdemo.dto.Person;
import com.example.restdemo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;

    public Person addMessageToPerson(int id, Message message) {
        Optional<Person> personOptional = repository.findById(id);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            person.getMessages().add(message);
            return repository.save(person);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Person not found");
        }
    }

    public void deleteMessage(int personId, int messageId) {
        Optional<Person> personOptional = repository.findById(personId);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            person.getMessages().removeIf(message -> message.getId() == messageId);
            repository.save(person);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        }
    }

    public List<Message> getMessagesByPersonId(int id) {
        Optional<Person> personOptional = repository.findById(id);
        if (personOptional.isPresent()) {
            return personOptional.get().getMessages();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        }
    }
}