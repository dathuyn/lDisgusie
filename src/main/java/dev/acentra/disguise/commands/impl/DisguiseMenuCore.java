package dev.acentra.disguise.commands.impl;

import dev.acentra.disguise.commands.BaseCommand;
import dev.acentra.disguise.utils.chat.CC;
import dev.acentra.disguise.utils.permissions.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DisguiseMenuCore extends BaseCommand {

    private final FileConfiguration config;

    public DisguiseMenuCore(FileConfiguration config) {
        super(Permissions.DISGUISECORE, true);
        this.config = config;
    }
    public static void addLore(ItemStack itemStack, List<String> loreLines) {
        if (itemStack == null || loreLines == null || loreLines.isEmpty()) {
            return;
        }

        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return;
        }

        List<String> itemLore = meta.getLore();
        if (itemLore == null) {
            itemLore = new ArrayList<>();
        }

        itemLore.addAll(loreLines);
        meta.setLore(itemLore);
        itemStack.setItemMeta(meta);
    }

    public static void setDisplayName(ItemStack itemStack, String displayName) {
        if (itemStack == null || displayName == null) {
            return;
        }

        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return;
        }

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        itemStack.setItemMeta(meta);
    }

    @Override
    public boolean handle(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Inventory inventory = Bukkit.createInventory(null, 27, CC.translate(config.getString("menu.title")));

        ItemStack glass = new ItemStack(Material.valueOf(config.getString("menu.glass_material")));
        ItemStack[] menuItems = new ItemStack[3];
        String[] keys = {"disguise", "nickname", "rank_change"};

        for (String key : keys) {
            ConfigurationSection section = config.getConfigurationSection("menu.items." + key);
            if (section != null) {
                ItemStack item = new ItemStack(Material.valueOf(section.getString("material")));
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(CC.translate(section.getString("display_name")));
                    List<String> lore = section.getStringList("lore");
                    if (lore != null && !lore.isEmpty()) {
                        addTranslatedLore(meta, lore);
                    }
                    item.setItemMeta(meta);
                }
                switch (key) {
                    case "disguise":
                        menuItems[0] = item;
                        break;
                    case "nickname":
                        menuItems[1] = item;
                        break;
                    case "rank_change":
                        menuItems[2] = item;
                        break;
                }
            }
        }

        int[] glassSlots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 18, 19, 20 , 21 , 22 , 23 , 24 , 25 , 26};
        for (int slot : glassSlots) {
            inventory.setItem(slot, glass);
        }

        inventory.setItem(11, menuItems[0]);
        inventory.setItem(13, menuItems[1]);
        inventory.setItem(15, menuItems[2]);

        player.openInventory(inventory);
        return true;
    }


    public static void addTranslatedLore(ItemMeta itemMeta, List<String> loreLines) {
        if (itemMeta == null || loreLines == null || loreLines.isEmpty()) {
            return;
        }

        List<String> translatedLore = new ArrayList<>();
        for (String line : loreLines) {
            translatedLore.add(CC.translate(line));
        }

        itemMeta.setLore(translatedLore);
    }


}
