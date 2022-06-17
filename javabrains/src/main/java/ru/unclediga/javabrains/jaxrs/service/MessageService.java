package ru.unclediga.javabrains.jaxrs.service;

import ru.unclediga.javabrains.jaxrs.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageService {

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(1L, "Hello, World!", "unclediga"));
        messages.add(new Message(2L, "Hello, Jersey", "unclediga"));
        return messages;
    }
}
