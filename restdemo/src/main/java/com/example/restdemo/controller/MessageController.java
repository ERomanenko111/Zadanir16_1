package com.example.restdemo.controller;

import com.example.restdemo.dto.Message;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    private List<Message> messageList = new ArrayList<>();

    @GetMapping
    public List<Message> getAllMessage() {
        return messageList;
    }

    @PostMapping
    public void addMessage(@RequestBody Message message) {
        messageList.add(message);
    }

    @GetMapping("/{id}")
    public Message getMessageById(@PathVariable int id) {
        for (Message m : messageList) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    @PutMapping("/{id}")
    public void updateMessageById(@PathVariable int id, @RequestBody Message updatedMessage) {
        for (Message m : messageList) {
            if (m.getId() == id) {
                m.setTitle(updatedMessage.getTitle());
                m.setText(updatedMessage.getText());
                m.setTime(updatedMessage.getTime());
            }
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMessageById(@PathVariable int id) {
        messageList.removeIf(m -> m.getId() == id);
    }
}