package dev.acentra.disguise.commands.impl;

import dev.acentra.disguise.commands.BaseCommand;
import dev.acentra.disguise.event.DisguiseAction;
import dev.acentra.disguise.event.DisguiseEvent;
import dev.acentra.disguise.utils.permissions.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisguiseRemoveCommand extends BaseCommand {
    public DisguiseRemoveCommand() {
        super(Permissions.DISGUISE, true);
    }

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        new DisguiseEvent(DisguiseAction.UNSET, player).call();

        return true;
    }
}
