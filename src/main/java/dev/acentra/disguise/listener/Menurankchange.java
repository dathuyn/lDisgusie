package dev.acentra.disguise.listener;

import dev.acentra.disguise.DisguisePlugin;
import dev.acentra.disguise.data.DisguiseData;
import dev.acentra.disguise.utils.NameUtils;
import dev.acentra.disguise.utils.chat.CC;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class Menurankchange implements Listener {
    private final DisguisePlugin plugin;
    private final FileConfiguration config;
    private final Map<Integer, String> prefixMap = new HashMap<>();

    public Menurankchange(DisguisePlugin plugin, FileConfiguration config1) {
        this.plugin = plugin;
        this.config = config1;


        prefixMap.put(11, "Premium");
        prefixMap.put(12, "Hero");
        prefixMap.put(13, "Elite");
        prefixMap.put(14, "Supreme");
        prefixMap.put(15, "Acentra");
        prefixMap.put(18, "NoRank");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory != null && clickedInventory.equals(player.getOpenInventory().getTopInventory()) &&
                event.getView().getTitle().equalsIgnoreCase(CC.translate("&8[ &0&lDisguise&8 ] &0&lRank Change"))) {
            int slot = event.getRawSlot();
            if (prefixMap.containsKey(slot)) {
                String selectedRank = prefixMap.get(slot);
                setPlayerRank(player, selectedRank);
            }

            event.setCancelled(true);
        }
    }

    private void setPlayerRank(Player player, String rank) {
        DisguiseData data = DisguisePlugin.dataManager().getData(player.getUniqueId());
        if (data != null) {
            data.setSelectedRank(rank);
            player.sendMessage(CC.translate("&8[&eDisguise Core&8]&f You have successfully selected the " + CC.translate(rank) + " rank."));
        } else {
            player.sendMessage(CC.translate("&8[&eDisguise Core&8]&f Error: Unable to find player data."));
        }
    }
}