package ru.unclediga.javabrains.jaxrs.service;

import ru.unclediga.javabrains.jaxrs.data.DatabaseClass;
import ru.unclediga.javabrains.jaxrs.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MessageService {

    private final Map<Long, Message> messages = DatabaseClass.getMessages();

    public List<Message> getAllMessages() {
        return new ArrayList<>(messages.values());
    }

    public List<Message> getAllMessages(int year) {
        if (year > 0){
            // i don't learn Date() :(
            return messages
                    .values()
                    .stream()
                    .filter(Objects::nonNull).collect(Collectors.toList());
        }
        return null;
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
        return messages.put(message.getId(), message);
    }

    public void removeMessage(long id){
        messages.remove(id);
    }
}
