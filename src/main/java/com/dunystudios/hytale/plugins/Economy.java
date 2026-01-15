package com.dunystudios.hytale.plugins;

import com.dunystudios.hytale.plugins.config.ConfigManager;
import com.dunystudios.hytale.plugins.json.PlayerManager;
import com.dunystudios.hytale.plugins.json.UserProfile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class Economy implements IEcoAPI {
    private final PlayerManager playerManager;
    private final ConfigManager configManager;

    public Economy(PlayerManager playerManager, ConfigManager configManager) {
        this.playerManager = playerManager;
        this.configManager = configManager;
    }

    @Override
    public float getBalance(UUID uuid) {
        return Float.parseFloat(
                new BigDecimal(
                        playerManager.getProfile(uuid).balance
                )
                        .setScale(configManager.getData().chat.decimal_places,
                                RoundingMode.DOWN)
                        .toString());
    }

    @Override
    public void addBalance(UUID uuid, float v) {
        UserProfile userProfile = playerManager.getProfile(uuid);
        userProfile.setBalance(userProfile.balance + v);
        playerManager.updateProfile(uuid, userProfile);
    }

    @Override
    public void removeBalance(UUID uuid, float v) {
        UserProfile userProfile = playerManager.getProfile(uuid);
        userProfile.setBalance(userProfile.balance - v);
        playerManager.updateProfile(uuid, userProfile);
    }

    @Override
    public void setBalance(UUID uuid, float v) {
        UserProfile userProfile = playerManager.getProfile(uuid);
        userProfile.setBalance(v);
        playerManager.updateProfile(uuid, userProfile);
    }
}
