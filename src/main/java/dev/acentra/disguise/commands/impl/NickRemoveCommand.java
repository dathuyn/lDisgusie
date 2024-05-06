package dev.acentra.disguise.commands.impl;

import dev.acentra.disguise.commands.BaseCommand;
import dev.acentra.disguise.data.DisguiseData;
import dev.acentra.disguise.utils.PlayerUtils;
import dev.acentra.disguise.utils.chat.CC;
import dev.acentra.disguise.utils.permissions.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class NickRemoveCommand extends BaseCommand {
    private final FileConfiguration config;
    public NickRemoveCommand(FileConfiguration config) {
        super(Permissions.NICK, true);
        this.config = config;
    }

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        DisguiseData data = dataManager.getData(player.getUniqueId());

        if (data.isDisguised()) {
            return error(sender, CC.translate(config.getString("messages.error-disguised")));
        }
        if (!data.isNicked()) {
            return error(sender, CC.translate(config.getString("messages.error-not-nicked")));
        }

        data.setNicked(false);

        String realName = data.getRealName();
        String nickName = data.getNickName();

        String message = CC.translate(String.format(config.getString("messages.reset-nickname"), realName + " &7(unnick)"));
        api.alert(message);

        PlayerUtils.removePlayerNick(player);

        data.setHidingRank(false);

        return true;
    }
}
