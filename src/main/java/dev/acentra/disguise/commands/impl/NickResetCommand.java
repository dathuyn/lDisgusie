package dev.acentra.disguise.commands.impl;

import dev.acentra.disguise.commands.BaseCommand;
import dev.acentra.disguise.data.DisguiseData;
import dev.acentra.disguise.event.DisguiseAction;
import dev.acentra.disguise.event.DisguiseEvent;
import dev.acentra.disguise.utils.PlayerUtils;
import dev.acentra.disguise.utils.chat.CC;
import dev.acentra.disguise.utils.permissions.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class NickResetCommand extends BaseCommand {

    private final FileConfiguration config;


    public NickResetCommand(FileConfiguration config) {
        super(Permissions.RESET, true);
        this.config = config;
    }

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return error(sender, CC.translate(config.getString("messages.no-player-name")));
        }

        String name = args[0];

        if (name.equalsIgnoreCase(sender.getName())) {
            return error(sender, CC.translate(config.getString("messages.reset-self")));
        }

        DisguiseData data = dataManager.getDataByName(name);

        if (data == null) {
            return error(sender, CC.translate(config.getString("messages.player-not-exist")));
        }
        if (!data.isNicked()) {
            return error(sender, CC.translate(config.getString("messages.player-not-nicked")));
        }

        String realName = data.getRealName();
        String nickedName = data.getNickName();

        String message = CC.translate(String.format(config.getString("messages.nick-reset"), nickedName, realName));
        sender.sendMessage(message);

        PlayerUtils.removePlayerNick(data);

        return false;
    }
}
