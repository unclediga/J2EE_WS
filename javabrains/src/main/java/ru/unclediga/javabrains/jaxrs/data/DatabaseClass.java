package ru.unclediga.javabrains.jaxrs.data;

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
        messages.put(1L, new Message(1L, "Hello, World!", "unclediga"));
        messages.put(2L, new Message(2L, "Hello, Jersey", "unclediga"));
    }

    public static void initProfiles() {
        profiles.clear();
        profiles.put("a1", new Profile(1L, "a1", "A1", "prof", new Date()));
        profiles.put("a2", new Profile(2L, "a2", "A2", "prof", new Date()));
        profiles.put("test", new Profile(3L, "test", "TEST", "prof", new Date()));
    }
}
