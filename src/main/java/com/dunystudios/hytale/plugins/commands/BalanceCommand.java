package com.dunystudios.hytale.plugins.commands;

import com.dunystudios.hytale.plugins.HyEconomy;
import com.dunystudios.hytale.plugins.config.ConfigManager;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.entity.entities.Player;

import javax.annotation.Nonnull;

public class BalanceCommand extends CommandBase {
    private final ConfigManager configManager;

    public BalanceCommand(@Nonnull String name, @Nonnull String description) {
        super(name, description);
        this.addAliases("bal", "money");
        this.setPermissionGroup(GameMode.Adventure);

        this.configManager = HyEconomy.getInstance().getConfigManager();
    }

    @Override
    protected void executeSync(@Nonnull CommandContext commandContext) {
        if (commandContext.sender() instanceof Player) {
            try {
                commandContext.sender().sendMessage(
                        Message.raw(
                                configManager.getData().messages.balance_check.replace(
                                        "{balance}", String.valueOf(HyEconomy.getInstance().getEconomy().getBalance(commandContext.sender().getUuid()))
                                ).replace(
                                        "{currency}", configManager.getData().chat.currency
                                ).replace(
                                        "{max_balance}", String.valueOf(configManager.getData().balance.maximum)
                                ).replace(
                                        "{min_balance}", String.valueOf(configManager.getData().balance.minimum)
                                )
                        )
                );
            } catch (Exception e) {
                commandContext.sender().sendMessage(
                        Message.raw(
                                configManager.getData().messages.error_occurred
                        )
                );
                throw new RuntimeException(e);
            }
        } else {
            commandContext.sender().sendMessage(
                    Message.raw("This command can only be executed by a player.")
            );
        }
    }
}
