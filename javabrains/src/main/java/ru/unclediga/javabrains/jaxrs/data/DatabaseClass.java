package ru.unclediga.javabrains.jaxrs.data;

import ru.unclediga.javabrains.jaxrs.model.Comment;
import ru.unclediga.javabrains.jaxrs.model.Message;
import ru.unclediga.javabrains.jaxrs.model.Profile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DatabaseClass {
    private static final Map<Long, Message> messages = new HashMap<>();
    private static final Map<String, Profile> profiles = new HashMap<>();

    static {
        initMessages();
        initProfiles();
    }

    public static Map<Long, Message> getMessages() {
        return messages;
    }

    public static Map<String, Profile> getProfiles() {
        return profiles;
    }

    public static void initMessages() {
        messages.clear();
        final Message m1 = new Message(1L, "Hello, World!", "unclediga");
        final Map<Long, Comment> comments1 = m1.getComments();
        comments1.put(1L, new Comment(1L, "Comment 1-1"));
        comments1.put(2L, new Comment(2L, "Comment 1-2"));
        comments1.put(3L, new Comment(3L, "Comment 1-3"));
        messages.put(1L, m1);

        final Message m2 = new Message(2L, "Hello, Jersey", "unclediga");
        final Map<Long, Comment> comments2 = m2.getComments();
        comments2.put(1L, new Comment(1L, "Comment 2-1"));
        comments2.put(2L, new Comment(2L, "Comment 2-2"));
        comments2.put(3L, new Comment(3L, "Comment 2-3"));
        messages.put(2L, m2);
    }

    public static void initProfiles() {
        profiles.clear();
        profiles.put("a1", new Profile(1L, "a1", "A1", "prof", new Date()));
        profiles.put("a2", new Profile(2L, "a2", "A2", "prof", new Date()));
        profiles.put("test", new Profile(3L, "test", "TEST", "prof", new Date()));
    }
}
