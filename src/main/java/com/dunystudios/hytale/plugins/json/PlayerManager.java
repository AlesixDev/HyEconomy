package com.dunystudios.hytale.plugins.json;

import com.dunystudios.hytale.plugins.HyEconomy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<UUID, UserProfile> cache;
    private final Path dataFolder;

    public PlayerManager() {
        this.cache = new ConcurrentHashMap<>();
        this.dataFolder = Path.of("HyEconomy", "profiles");
        File folder = dataFolder.toFile();
        if (!folder.exists()) folder.mkdirs();
    }

    public void loadPlayerData(UUID uuid) {
        File file = dataFolder.resolve(uuid.toString() + ".json").toFile();

        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                UserProfile profile = gson.fromJson(reader, UserProfile.class);
                cache.put(uuid, profile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            UserProfile profile = new UserProfile();
            profile.setUuid(uuid.toString());
            profile.setBalance(HyEconomy.getInstance().getConfigManager().getData().balance.starting);
            cache.put(uuid, profile);
        }
    }

    public UserProfile getProfile(UUID uuid) {
        return cache.get(uuid);
    }

    public void updateProfile(UUID uuid, UserProfile profile) {
        cache.put(uuid, profile);
    }

    public void savePlayerData(UUID uuid) {
        UserProfile profile = cache.get(uuid);

        File file = dataFolder.resolve(uuid.toString() + ".json").toFile();
        HyEconomy.getInstance().getLogger().atInfo().log("Saving data for player " + uuid + " in file " + file.getAbsolutePath());
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(profile, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unloadPlayer(UUID uuid) {
        savePlayerData(uuid);
        cache.remove(uuid);
    }

    public void unloadAll() {
        for (UUID uuid : cache.keySet()) {
            savePlayerData(uuid);
        }
        cache.clear();
    }
}
