package ru.unclediga.javabrains.jaxrs.service;

import ru.unclediga.javabrains.jaxrs.data.DatabaseClass;
import ru.unclediga.javabrains.jaxrs.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageService {

    private final Map<Long, Message> messages = DatabaseClass.getMessages();

    public MessageService() {
        messages.put(1L, new Message(1L, "Hello, World!", "unclediga"));
        messages.put(2L, new Message(2L, "Hello, Jersey", "unclediga"));
    }

    public List<Message> getAllMessages() {
        return new ArrayList<>(messages.values());
    }

    public Message getMessage(long id){
        return messages.get(id);
    }

    public Message addMessage(Message message){
        message.setId(messages.size() + 1);
        messages.put(message.getId(), message);
        return message;
    }

    public Message updateMessage(Message message){
        return messages.putIfAbsent(message.getId(), message);
    }

    public Message removeMessage(long id){
        return messages.remove(id);
    }
}
