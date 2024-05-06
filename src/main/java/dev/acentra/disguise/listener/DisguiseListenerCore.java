package dev.acentra.disguise.listener;

import dev.acentra.disguise.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DisguiseListenerCore implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase(CC.translate("&8[ &e&lDisguise&8 ] &f&lDisguise Control"))) {
            return;
        }
        int slot = event.getRawSlot();
        if (slot == 11) {
            Player player = (Player) event.getWhoClicked();
            player.performCommand("disguise");
        }
        if (slot == 13){
            Player player = (Player) event.getWhoClicked();
            player.performCommand("nick");
        }
        if (slot == 15){
            Player player = (Player) event.getWhoClicked();
            player.performCommand("disguise-rank");
        }
        event.setCancelled(true);
        }
}
