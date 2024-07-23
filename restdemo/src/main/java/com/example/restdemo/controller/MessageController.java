package com.example.restdemo.controller;

import com.example.restdemo.dto.Message;
import com.example.restdemo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    public List<Message> getAllMessage() {
        return (List<Message>) messageRepository.findAll();
    }

    @PostMapping("/add")
    public Message addMessage(@RequestBody Message message) {
        return messageRepository.save(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessageById(@PathVariable int id, @RequestBody Message updatedMessage) {
        Optional<Message> existingMessage = messageRepository.findById(id);
        if (existingMessage.isPresent()) {
            updatedMessage.setId(id);
            messageRepository.save(updatedMessage);
            return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMessageById(@PathVariable int id) {
        messageRepository.deleteById(id);
    }
}