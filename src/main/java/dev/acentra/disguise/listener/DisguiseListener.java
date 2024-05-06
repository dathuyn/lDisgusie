package dev.acentra.disguise.listener;

import dev.acentra.disguise.DisguisePlugin;
import dev.acentra.disguise.data.DisguiseData;
import dev.acentra.disguise.event.DisguiseAction;
import dev.acentra.disguise.event.DisguiseEvent;
import dev.acentra.disguise.utils.PlayerUtils;
import dev.acentra.disguise.utils.chat.CC;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;
import java.util.UUID;

public class DisguiseListener implements Listener {

    @EventHandler
    public void onDisguise(DisguiseEvent event) {

        DisguiseData data = event.getDisguiseData();
        Player player = event.getPlayer();

        // Unused, I have no clue what this is even for.
        //Set<UUID> alerted = DisguisePlugin.api().getAlerted();

        boolean alert = false;

        switch (event.getAction()) {
            case SET: {
                if (data.isDisguised()) {
                    player.sendMessage(CC.translate("&8[&eDisguise Core&8]&c You are already disguised!"));
                    break;
                }

                data.setDisguised(true);
                data.setDisguisedName(event.getDisguiseName());

                (new BukkitRunnable() {
                    @Override
                    public void run() {
                        PlayerUtils.setPlayerDisguise(player, event.getDisguiseName(), event.isRandom());
                    }
                }).runTaskAsynchronously(DisguisePlugin.getPlugin());

                alert = true;

                break;
            }

            case UNSET:
            case RESET: {
                if (!data.isDisguised()) {
                    player.sendMessage(CC.translate("&8[&eDisguise Core&8]&c You are not disguised!"));
                    break;
                }

                data.setDisguised(false);

                (new BukkitRunnable() {
                    @Override
                    public void run() {
                        PlayerUtils.removePlayerDisguise(player);
                    }
                }).runTaskAsynchronously(DisguisePlugin.getPlugin());

                alert = true;

                break;
            }
        }

        if (!alert) return;

        String realName = data.getRealName();
        String disguiseName = data.getDisguisedName();

        boolean set = event.getAction() == DisguiseAction.SET;
        String message = CC.translate(String.format("&8[&eDisguise Core&8]&f You disguise yourself from &c%s&f to &a%s", set ? realName : disguiseName, set ? disguiseName : realName));

        boolean unset = event.getAction() == DisguiseAction.UNSET;
        String messageunset = CC.translate(String.format("&8[&eDisguise Core&8]&f You take off your disguise and reset from &c%s&f back to &a%s", set ? realName : disguiseName, set ? disguiseName : realName));

        if (set){
            DisguisePlugin.api().alert(message);
        } else {
            DisguisePlugin.api().alert(messageunset);
        }


        // Reset disguise name as to the real name
        // if (!set) data.setDisguisedName(data.getRealName());
    }
}
