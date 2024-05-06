package dev.acentra.disguise.utils;

import dev.acentra.disguise.DisguisePlugin;
import dev.acentra.disguise.data.DisguiseData;
import dev.acentra.disguise.utils.chat.CC;
import dev.acentra.disguise.utils.chat.Console;
import dev.iiahmed.disguise.Disguise;
import dev.iiahmed.disguise.DisguiseProvider;
import dev.iiahmed.disguise.SkinAPI;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class PlayerUtils {



    public static void setPlayerNick(Player p, String nick) {
        DisguiseProvider provider = DisguisePlugin.getPlugin().getProvider();

        Disguise nickDisguise = Disguise.builder().setName(nick).build();
        provider.disguise(p, nickDisguise);

        p.setDisplayName(nick);
        p.setPlayerListName(nick);

        provider.refreshAsPlayer(p);

        p.sendMessage(CC.translate("&8[&eDisguise Core&8]&a You are now nicked as &f" + nick));
    }

    public static void removePlayerNick(DisguiseData data) {

        String realName = data.getRealName();
        Player disguisedPlayer = data.getPlayer();
        DisguiseProvider provider = DisguisePlugin.getPlugin().getProvider();

        if(provider.isDisguised(disguisedPlayer)) {

            provider.undisguise(disguisedPlayer);

            data.getPlayer().setDisplayName(realName);
            data.getPlayer().setPlayerListName(realName);

            provider.refreshAsPlayer(disguisedPlayer);

            data.getPlayer().spigot().sendMessage(new TextComponent(CC.translate("&8[&eDisguise Core&8]&a You have been un-nicked")));;
        }
    }

    public static void removePlayerNick(Player player) {
        removePlayerNick(DisguisePlugin.api().getDataManager().getData(player.getUniqueId()));
    }

    public static void setPlayerDisguise(Player p, String disguiseName, boolean noSkin) {

        DisguiseProvider provider = DisguisePlugin.getPlugin().getProvider();

        Disguise playerDisguise = Disguise.builder()
                        .setName(disguiseName).setSkin(noSkin ? "Steve" : disguiseName, SkinAPI.MOJANG)
                        .setEntityType(EntityType.PLAYER).build();

        provider.disguise(p, playerDisguise);

        p.setDisplayName(p.getName());
        p.setPlayerListName(p.getName());

        provider.refreshAsPlayer(p);

        p.sendMessage(CC.translate("&8[&eDisguise Core&8]&a You are now disguised as &f" + disguiseName));
        Console.sendMessage(String.format("&8[&eDisguise Core&8] %s disguised as %s", p.getName(), disguiseName));
    }


    public static void removePlayerDisguise(Player p) {
        DisguiseProvider provider = DisguisePlugin.getPlugin().getProvider();
        if (provider.isDisguised(p)) {

            provider.undisguise(p);

            // If a player previously ran /skin <name> we must revert to that earlier state!

            p.setDisplayName(p.getName());
            p.setPlayerListName(p.getName());

            provider.refreshAsPlayer(p);

            p.sendMessage(CC.translate("&8[&eDisguise Core&8]&a You have been undisguised"));

            Console.sendMessage(String.format("&8[&eDisguise Core&8] %s undisguised", p.getName()));
        }
    }
}
