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

public class RemoveCommand extends AbstractAsyncCommand {
    private final RequiredArg<PlayerRef> playerName;
    private final RequiredArg<Float> amount;
    private final ConfigManager configManager;

    public RemoveCommand(@Nonnull String name, @Nonnull String description) {
        super(name, description);

        this.configManager = HyEconomy.getInstance().getConfigManager();

        playerName = withRequiredArg("player", "The player to remove money to", ArgTypes.PLAYER_REF)
                .suggest((sender, text, paramCount, result) -> {
                    for (PlayerRef playerRef : Universe.get().getPlayers()) {
                        if (playerRef.getUsername().toLowerCase().startsWith(text.toLowerCase())) {
                            result.suggest(playerRef);
                        }
                    }
                });
        amount = withRequiredArg("amount", "The amount of money to remove", ArgTypes.FLOAT);
    }

    @Nonnull
    @Override
    protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext commandContext) {
            try {
                PlayerRef targetPlayer = playerName.get(commandContext);
                float amountToRemove = amount.get(commandContext);
                final float currentPlayerBalance = HyEconomy.getInstance().getEconomy().getBalance(targetPlayer.getUuid());

                if (currentPlayerBalance - amountToRemove < configManager.getData().balance.minimum) {
                    commandContext.sender().sendMessage(
                            Message.raw(
                                    configManager.getData().messages.min_balance_reached.replace("{player}", targetPlayer.getUsername())
                                            .replace("{balance}", String.valueOf(amountToRemove)).replace("{currency}", configManager.getData().chat.currency)
                                            .replace("{min_balance}", String.valueOf(configManager.getData().balance.minimum))
                            )
                    );
                    return CompletableFuture.completedFuture(null);
                }

                if (currentPlayerBalance - amountToRemove > configManager.getData().balance.maximum) {
                    commandContext.sender().sendMessage(
                            Message.raw(
                                    configManager.getData().messages.max_balance_reached.replace("{player}", targetPlayer.getUsername())
                                            .replace("{balance}", String.valueOf(amountToRemove)).replace("{currency}", configManager.getData().chat.currency)
                                            .replace("{max_balance}", String.valueOf(configManager.getData().balance.maximum))
                            )
                    );
                    return CompletableFuture.completedFuture(null);
                }

                HyEconomy.getInstance().getEconomy().removeBalance(targetPlayer.getUuid(), amountToRemove);

                commandContext.sender().sendMessage(
                        Message.raw(
                                configManager.getData().messages.admin_remove_success.replace("{player}", targetPlayer.getUsername())
                                        .replace("{balance}", String.valueOf(amountToRemove)).replace("{currency}", configManager.getData().chat.currency)
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
