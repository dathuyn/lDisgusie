package dev.acentra.disguise.commands.impl;

import dev.acentra.disguise.commands.BaseCommand;
import dev.acentra.disguise.data.DisguiseData;
import dev.acentra.disguise.event.DisguiseAction;
import dev.acentra.disguise.event.DisguiseEvent;
import dev.acentra.disguise.utils.chat.CC;
import dev.acentra.disguise.utils.permissions.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DisguiseResetCommand extends BaseCommand {
    private final FileConfiguration config;
    public DisguiseResetCommand(FileConfiguration config) {
        super(Permissions.RESET, true);
        this.config = config;
    }
    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return error(sender, CC.translate(config.getString("messages.missing-player-name")));
        }

        String name = args[0];

        if (name.equalsIgnoreCase(sender.getName())) {
            return error(sender, CC.translate(config.getString("messages.reset-self")));
        }

        DisguiseData data = dataManager.getDataByName(name);

        if (data == null) {
            return error(sender, CC.translate(config.getString("messages.player-not-exist")));
        }
        if (!data.isDisguised()) {
            return error(sender, CC.translate(config.getString("messages.player-not-disguised")));
        }

        String realName = data.getRealName();
        String disguiseName = data.getDisguisedName();

        String message = CC.translate(String.format("&8[&eDisguise Core&8] &cReset&7:&f Player &c%s &fbecomes &a%s", disguiseName, realName));

        sender.sendMessage(message);

        new DisguiseEvent(DisguiseAction.RESET, (Player) sender).call();

        return false;
    }
}
