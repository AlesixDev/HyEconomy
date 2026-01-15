package com.dunystudios.hytale.plugins.events;

import com.dunystudios.hytale.plugins.HyEconomy;
import com.dunystudios.hytale.plugins.json.PlayerManager;
import com.dunystudios.hytale.plugins.json.UserProfile;
import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;

import java.util.UUID;

public class PlayerJoin {
    public PlayerJoin() {
        PlayerManager playerManager = HyEconomy.getInstance().getPlayerManager();
        HyEconomy.getInstance().getEventRegistry().register(
                PlayerConnectEvent.class, event -> {
                    UUID playerUuid = event.getPlayerRef().getUuid();
                    playerManager.loadPlayerData(playerUuid);
                    UserProfile profile = playerManager.getProfile(playerUuid);
                    if (profile != null) {
                        HyEconomy.getInstance().getLogger().atInfo().log("Loaded data for player " + playerUuid);
                        HyEconomy.getInstance().getLogger().atInfo().log("Player " + playerUuid + " joined. Balance: " + profile.balance);
                    } else {
                        HyEconomy.getInstance().getLogger().atSevere().log("Failed to load profile for " + playerUuid);
                    }

                }
        );
    }
}
