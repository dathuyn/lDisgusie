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
import java.util.stream.Collectors;

public class NickListCommand extends BaseCommand {
    private final FileConfiguration config;
    public NickListCommand(FileConfiguration config) {
        super(Permissions.NICK_LIST, false);
        this.config = config;
    }

    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        List<DisguiseData> nicked = dataManager.getDisguised().values().stream().filter(DisguiseData::isNicked).collect(Collectors.toList());

        if (nicked.isEmpty()) {
            String message = config.getString("messages.no-one-nicked");
            sender.sendMessage(CC.translate(message));
            return true;
        }

        String listMessage = formatConfigMessage(config, "messages.nick-list", nicked.size());

        sender.sendMessage(listMessage);

        for (DisguiseData disguiseData : nicked) {
            String entryMessage = formatConfigMessage(config, "messages.nick-entry", disguiseData.getRealName(), disguiseData.getNickName());
            sender.sendMessage(entryMessage);
        }

        return true;
    }
    private String formatConfigMessage(FileConfiguration config, String path, Object... args) {
        String message = config.getString(path);
        if (message != null) {
            message = String.format(message, args);
            return CC.translate(message);
        } else {
            return "";
        }
    }
}

