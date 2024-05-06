package dev.acentra.disguise.listener;

import dev.acentra.disguise.commands.impl.RankCommandChange;
import dev.acentra.disguise.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class MenuListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ClickType type = event.getClick();

        InventoryHolder holder = event.getInventory().getHolder();
        ItemStack item = event.getCurrentItem();

        if (!(holder instanceof Menu) || item == null || item.getType() == Material.AIR) return;

        event.setCancelled(true);

        Menu menu = (Menu) holder;

        menu.handleClick(event);
    }
}
