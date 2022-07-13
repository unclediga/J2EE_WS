package ru.unclediga.javabrains.jaxrs.service;

import ru.unclediga.javabrains.jaxrs.data.DatabaseClass;
import ru.unclediga.javabrains.jaxrs.model.Message;
import ru.unclediga.javabrains.jaxrs.model.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileService {

    private final Map<String, Profile> profiles = DatabaseClass.getProfiles();

    public List<Profile> getAllProfiles() {
        return new ArrayList<>(profiles.values());
    }

    public Profile getProfile(String profileName) {
        return profiles.get(profileName);
    }

    public Profile addProfile(Profile profile) {
        profile.setId(profiles.size() + 1);
        profiles.put(profile.getProfileName(), profile);
        return profile;
    }

    public Profile updateProfile(Profile profile) {
        if (profile.getProfileName().isEmpty()) {
            return null;
        }
        profiles.put(profile.getProfileName(), profile);
        return profile;
    }

    public Profile removeProfile(String profileName) {
        return profiles.remove(profileName);
    }
}
