package dev.acentra.disguise.commands;

import dev.acentra.disguise.DisguiseAPI;
import dev.acentra.disguise.data.DataManager;
import dev.acentra.disguise.utils.chat.CC;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public abstract class BaseCommand implements CommandExecutor {
    private final String permission;
    private final boolean playerOnly;

    @Setter
    protected DisguiseAPI api;

    @Setter
    protected DataManager dataManager;


    public BaseCommand(String permission, boolean playerOnly) {
        this.permission = permission;
        this.playerOnly = playerOnly;
    }



    public boolean error(CommandSender sender, String message) {
        sender.sendMessage(message);
        return true;
    }

    public boolean isBlackListed(String[] args) {
        if (args.length == 0) return false;

        return api.getBlacklist().contains(args[0].toLowerCase());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (playerOnly && !(sender instanceof Player)) {
            String playerOnlyMessage = CC.translate("&8[&eDisguise Core&8] &cOnly players can use this command.");
            sender.sendMessage(CC.translate(playerOnlyMessage));
            return true;
        }

        if (!sender.hasPermission(permission)) {
            String noPermissionMessage = CC.translate("&8[&eDisguise Core&8]&c I'm sorry but you don't have permission to use this command.");
            sender.sendMessage(CC.translate(noPermissionMessage));
            return true;
        }

        return handle(sender, command, label, args);
    }

    public abstract boolean handle(CommandSender sender, Command command, String label, String[] args);
}
