package com.example.restdemo.controller;

import com.example.restdemo.dto.Message;
import com.example.restdemo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void updateMessageById(@PathVariable int id, @RequestBody Message updatedMessage) {
        Message existingMessage = messageRepository.findById(id).orElse(null);
        if (existingMessage != null) {
            updatedMessage.setId(id);
            messageRepository.save(updatedMessage);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMessageById(@PathVariable int id) {
        messageRepository.deleteById(id);
    }
}