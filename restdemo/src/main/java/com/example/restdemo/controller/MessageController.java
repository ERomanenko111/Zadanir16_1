package com.example.restdemo.controller;

import com.example.restdemo.dto.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RestController
@RequestMapping("/message")
public class MessageController {
    private final List<Message> messageList = new ArrayList<>();

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
        int index = -1;
        for (int i = 0; i < messageList.size(); i++) {
            Message m = messageList.get(i);
            if (m.getId() == id) {
                m.setTitle(updatedMessage.getTitle());
                m.setText(updatedMessage.getText());
                m.setTime(updatedMessage.getTime());
                index = i; // Сохраняем индекс найденной записи
                break;
            }
        }

        if (index != -1) {
            // Обновляем найденную запись с сохранением её id
            Message oldMessage = messageList.get(index);
            messageList.set(index, new Message(oldMessage.getId(), updatedMessage.getTitle(), updatedMessage.getText(), updatedMessage.getTime()));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMessageById(@PathVariable int id) {
        messageList.removeIf(m -> m.getId() == id);
    }
}