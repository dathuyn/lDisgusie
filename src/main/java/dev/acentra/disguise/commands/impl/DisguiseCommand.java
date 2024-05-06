package dev.acentra.disguise.commands.impl;

import dev.acentra.disguise.DisguiseAPI;
import dev.acentra.disguise.DisguisePlugin;
import dev.acentra.disguise.commands.BaseCommand;
import dev.acentra.disguise.data.DisguiseData;
import dev.acentra.disguise.event.DisguiseAction;
import dev.acentra.disguise.event.DisguiseEvent;
import dev.acentra.disguise.utils.NameUtils;
import dev.acentra.disguise.utils.chat.CC;
import dev.acentra.disguise.utils.permissions.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class DisguiseCommand extends BaseCommand {

    private final FileConfiguration config;

    public DisguiseCommand(FileConfiguration config) {
        super(Permissions.DISGUISE, true);
        this.config = config;
    }

    ;

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!command.getName().equalsIgnoreCase("disguise")) return false;

        DisguiseData data = dataManager.getData(player.getUniqueId());
        if (data != null && data.isNicked()) {
            String message = config.getString("messages.cannot-disguise-while-nicked");
            player.sendMessage(CC.translate(message));
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("help")) {
            List<String> helpMessages = config.getStringList("help-messages.commands");
            sender.sendMessage(CC.translate(config.getString("help-messages.header")));
            sender.sendMessage(CC.translate(config.getString("help-messages.title")));
            for (String message : helpMessages) {
                sender.sendMessage(CC.translate(message));
            }
            sender.sendMessage(CC.translate(config.getString("help-messages.footer")));
            return true;
        } else if (args.length > 0) {
            if (!player.hasPermission(Permissions.DISGUISE_NAME)) {
                player.sendMessage(CC.translate(config.getString("messages.no-permission-disguise-args")));
                return true;
            }
            String disguiseName = args[0];
            if (api.getBlacklist().contains(disguiseName.toLowerCase())) {
                String message = config.getString("messages.blacklist-disguise")
                        .replace("%disguiseName%", disguiseName);
                player.sendMessage(CC.translate(message));
                return true;
            }
        }
        if (args.length > 1) {
            player.sendMessage(CC.translate("&cSử dụng: /disguise help"));
            return true;
        }

        if (dataManager.isDisguised(player.getUniqueId())) {
            String message = config.getString("messages.already-disguised");
            player.sendMessage(CC.translate(message));
            return true;
        }

        if (isBlackListed(args)) {
            String message = config.getString("messages.blacklisted-name");
            return error(sender, CC.translate(message));
        }

        final boolean random = args.length == 0;

        final DisguisePlugin plugin = DisguisePlugin.getPlugin();

        (new BukkitRunnable() {
            @Override
            public void run() {
                String disguiseName = random ? NameUtils.generate() : CC.strip(args[0]);
                (new BukkitRunnable() {
                    @Override
                    public void run() {
                        new DisguiseEvent(DisguiseAction.SET, player, disguiseName, random).call();
                    }
                }).runTask(plugin);
            }
        }).runTaskAsynchronously(plugin);
        return true;
    }

}