package com.dunystudios.hytale.plugins.events;

import com.dunystudios.hytale.plugins.HyEconomy;
import com.dunystudios.hytale.plugins.json.PlayerManager;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;

public class PlayerLeave {
    public PlayerLeave() {
        PlayerManager playerManager = HyEconomy.getInstance().getPlayerManager();
        HyEconomy.getInstance().getEventRegistry().register(
                PlayerDisconnectEvent.class, event -> {
                    if (!HytaleServer.get().isShuttingDown()) {
                        playerManager.unloadPlayer(event.getPlayerRef().getUuid());
                    }
                }
        );
    }
}
