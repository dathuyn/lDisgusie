package dev.acentra.disguise.commands.impl;

import dev.acentra.disguise.commands.BaseCommand;
import dev.acentra.disguise.data.DisguiseData;
import dev.acentra.disguise.utils.NameUtils;
import dev.acentra.disguise.utils.PlayerUtils;
import dev.acentra.disguise.utils.chat.CC;
import dev.acentra.disguise.utils.menu.Button;
import dev.acentra.disguise.utils.menu.Menu;
import dev.acentra.disguise.utils.permissions.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class NickCommand extends BaseCommand {


    private final FileConfiguration config;

    public NickCommand(FileConfiguration config) {
        super(Permissions.NICK, true);
        this.config = config;
    }

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        DisguiseData data = dataManager.getData(player.getUniqueId());


        if (command.getName().equalsIgnoreCase("nick")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("help")) {
                List<String> helpMessages = config.getStringList("help-messages-nick.commands");
                sender.sendMessage(CC.translate(config.getString("help-messages-nick.header")));
                sender.sendMessage(CC.translate(config.getString("help-messages-nick.title")));
                for (String message : helpMessages) {
                    sender.sendMessage(CC.translate(message));
                }
                sender.sendMessage(CC.translate(config.getString("help-messages-nick.footer")));
                return true;
            } else if (args.length > 0) {
                if (!player.hasPermission(Permissions.NICK_ARGS_NAME)) {
                    String message = config.getString("messages.no-permission-nick-args");
                    player.sendMessage(CC.translate(message));
                    return true;
                }
            }
                if (args.length > 0) {
                String nickName = args[0];
                    if (api.getBlacklist().contains(nickName.toLowerCase())) {
                        String message = config.getString("messages.blacklist-nickname")
                                .replace("%nickName%", nickName);
                        player.sendMessage(CC.translate(message));
                        return true;
                    }
            }

            if (data.isDisguised()) {
                String message = config.getString("messages.disguise-while-nicked");
                return error(sender, CC.translate(message));
            }

            if (data.isNicked()) {
                String message = config.getString("messages.already-nicked");
                return error(sender, CC.translate(message));
            }

            if (isBlackListed(args)) {
                String message = config.getString("messages.blacklisted-names");
                return error(sender, CC.translate(message));
            }

            Button acceptButton = Button.builder()
                    .displayName(CC.GREEN + "Enable")
                    .material(Material.WOOL)
                    .amount(1)
                    .type((byte) 5) // green
                    .onClick(clicker -> handleClick(data, args, false))
                    .build();

            Button declineButton = Button.builder()
                    .displayName(CC.RED + "Disable")
                    .material(Material.WOOL)
                    .amount(1)
                    .type((byte) 14) // red
                    .onClick(clicker -> handleClick(data, args, true))
                    .build();

            Menu.builder()
                    .title(CC.BLUE + "Nick Change")
                    .slots(3 * 9)
                    .player(player)
                    .setMenuItems(menu -> {
                        menu.addItem(12, acceptButton);
                        menu.addItem(14, declineButton);
                    })
                    .build().open();

            return true;
        }
        if (args.length < 1) {
            player.sendMessage(CC.translate("&cSử dụng: /nick help"));
            return true;
        }
    return false;
    }

    public void handleClick(DisguiseData data, String[] args, boolean state) {
        // Eliminate any unintended extra clicks
        if (data.isNicked()) return;

        data.setNicked(true);

        Player player = data.getPlayer();

        player.closeInventory();

        String nick = args.length == 0 ? NameUtils.generate() : args[0];

        nick = CC.strip(nick);

        data.setNickName(nick);

        PlayerUtils.setPlayerNick(player, nick);

        String realName = data.getRealName();
        String nickName = data.getNickName();

        /**
         * @see dev.acentra.disguise.placeholder.Placeholder
         */
        data.setHidingRank(state);

        String message = CC.translate(String.format("&8[&eDisguise Core&8]&f You changed the name from &c%s &fto &a%s", realName, nickName + " &7(nick)"));

        api.alert(message);
    }
}
