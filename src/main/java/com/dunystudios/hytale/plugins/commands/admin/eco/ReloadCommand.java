package com.dunystudios.hytale.plugins.commands.admin.eco;

import com.dunystudios.hytale.plugins.HyEconomy;
import com.dunystudios.hytale.plugins.config.ConfigManager;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class ReloadCommand extends AbstractAsyncCommand {
    private final ConfigManager configManager;

    public ReloadCommand(@Nonnull String name, @Nonnull String description) {
        super(name, description);
        this.addAliases("rel", "r");
        this.configManager = HyEconomy.getInstance().getConfigManager();
    }

    @Nonnull
    @Override
    protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext commandContext) {
        try {
            configManager.load();
            commandContext.sender().sendMessage(
                    Message.raw(
                            "Configuration reloaded successfully."
                    )
            );
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            commandContext.sender().sendMessage(
                    Message.raw(
                            configManager.getData().messages.error_occurred
                    )
            );
            throw new RuntimeException(e);
        }
    }
}
