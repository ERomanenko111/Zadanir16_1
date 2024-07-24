package com.example.restdemo.Service;

import com.example.restdemo.dto.Message;
import com.example.restdemo.dto.Person;
import com.example.restdemo.repository.PersonRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PersonService {
    @Autowired
    PersonRepository repository;

    public Person addMeesageToPerson(int personId, @NotNull Message message) {
        Person person = repository.findById(personId).get();
        message.setPerson(person);
        message.setTime(LocalDateTime.now());
        person.addMessage(message);
        return repository.save(person);
    }

}