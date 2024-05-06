package dev.acentra.disguise.commands.impl;

import dev.acentra.disguise.commands.BaseCommand;
import dev.acentra.disguise.data.DisguiseData;
import dev.acentra.disguise.utils.chat.CC;
import dev.acentra.disguise.utils.permissions.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class DisguiseRevealCommand extends BaseCommand {
    private final FileConfiguration config;
    public DisguiseRevealCommand(FileConfiguration config) {
        super(Permissions.REVEAL, false);
        this.config = config;
    }

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return error(sender, CC.translate(config.getString("messages.no-player-name")));
        }

        String name = args[0];

        if (name.equalsIgnoreCase(sender.getName())) {
            return error(sender, CC.translate(config.getString("messages.cannot-reveal-yourself")));
        }

        DisguiseData data = dataManager.getDataByName(name);


        if (data == null) {
            return error(sender, CC.translate(config.getString("messages.player-not-exist")));
        }

        if (!data.isDisguised()) {
            return error(sender, CC.translate(config.getString("messages.player-not-disguised")));
        }

        String message = CC.translate(String.format("&8[&eDisguise Core&8]&f Player &c%s &7is disguised as &a%s", data.getRealName(), data.getDisguisedName()));

        sender.sendMessage(message);

        return true;
    }
}
