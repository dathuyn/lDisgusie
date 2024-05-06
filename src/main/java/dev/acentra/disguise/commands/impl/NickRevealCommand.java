package dev.acentra.disguise.commands.impl;

import dev.acentra.disguise.commands.BaseCommand;
import dev.acentra.disguise.data.DisguiseData;
import dev.acentra.disguise.utils.chat.CC;
import dev.acentra.disguise.utils.permissions.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class NickRevealCommand extends BaseCommand {

    private final FileConfiguration config;

    public NickRevealCommand(FileConfiguration config) {
        super(Permissions.REVEAL, false);
        this.config = config;
    }

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            String errorMessage = config.getString("messages.no-player-name");
            return error(sender, CC.translate(errorMessage));
        }

        String name = args[0];

        if (name.equalsIgnoreCase(sender.getName())) {
            String errorMessage = config.getString("messages.cannot-reveal-yourself");
            return error(sender, CC.translate(errorMessage));
        }

        DisguiseData data = dataManager.getDataByName(name);

        if (data == null) {
            String errorMessage = config.getString("messages.player-not-exist");
            return error(sender, CC.translate(errorMessage));
        }

        if (!data.isNicked()) {
            String errorMessage = config.getString("messages.player-not-nicked");
            return error(sender, CC.translate(errorMessage));
        }

        String messageFormat = config.getString("messages.disguised-player-entrys");
        String realName = data.getRealName();
        String nickName = data.getNickName();
        String message = String.format(messageFormat, realName, nickName);

        sender.sendMessage(CC.translate(message));

        return true;
    }
}
