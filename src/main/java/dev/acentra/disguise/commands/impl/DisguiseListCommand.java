package dev.acentra.disguise.commands.impl;

import dev.acentra.disguise.commands.BaseCommand;
import dev.acentra.disguise.data.DisguiseData;
import dev.acentra.disguise.utils.chat.CC;
import dev.acentra.disguise.utils.permissions.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class DisguiseListCommand extends BaseCommand {

    private final FileConfiguration config;
    public DisguiseListCommand(FileConfiguration config) {
        super(Permissions.DISGUISE_LIST, false);
        this.config = config;
    }

    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        List<DisguiseData> disguised = dataManager.getDisguised().values().stream().filter(DisguiseData::isDisguised).collect(Collectors.toList());

        if (disguised.isEmpty()) {
            sender.sendMessage(CC.translate(config.getString("messages.no-one-disguised")));
            return true;
        }

        String message = CC.translate(String.format("&cList of all players using disguise: &7(&c%d&7)", disguised.size()));

        sender.sendMessage(message);

        for (DisguiseData disguiseData : disguised) {
            String messageEntry = String.format(config.getString("messages.disguised-player-entry"), disguiseData.getRealName(), disguiseData.getDisguisedName());
            sender.sendMessage(CC.translate(messageEntry));
        }

        return true;
    }
}

