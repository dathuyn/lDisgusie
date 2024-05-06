package dev.acentra.disguise.commands.impl;

import dev.acentra.disguise.commands.BaseCommand;
import dev.acentra.disguise.utils.chat.CC;
import dev.acentra.disguise.utils.permissions.Permissions;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankCommandChange extends BaseCommand {

    private final FileConfiguration config;
    public RankCommandChange(FileConfiguration config) {
        super(Permissions.RANKCHANGE, true);
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
        Inventory inventory = Bukkit.createInventory(null, 27, CC.translate(config.getString("menurank.title")));

        ItemStack non = createItem(Material.COAL, 1, config.getString("prefixess.NoRanks") + player.getName(), createLore(config, "lores"));

        ItemStack premium = createItem(Material.REDSTONE, 1, config.getString("prefixess.Premiums") + player.getName(), createLore(config, "lores"));
        ItemStack hero = createItem(Material.IRON_INGOT, 1, config.getString("prefixess.Heros") + player.getName(), createLore(config, "lores"));
        ItemStack elite = createItem(Material.GOLD_INGOT, 1, config.getString("prefixess.Elites") + player.getName(), createLore(config, "lores"));
        ItemStack supreme = createItem(Material.EMERALD, 1, config.getString("prefixess.Supremes") + player.getName(), createLore(config, "lores"));
        ItemStack acentra = createItem(Material.DIAMOND, 1, config.getString("prefixess.Acentras") + player.getName(), createLore(config, "lores"));
        ItemStack helpgui = createItem(Material.BOOK, 1, config.getString("prefixess.HelpGuis") + " Instructions", createLore(config, "helpguiLores"));
        ItemStack glass = createItem(Material.STAINED_GLASS_PANE, 1, "", Collections.singletonList(""));


        for (int slot : new int[]{0, 8, 9, 10, 16, 17, 19, 20, 21, 23, 24, 22, 25}) {
            inventory.setItem(slot, glass);
        }

        inventory.setItem(11, premium);
        inventory.setItem(12, hero);
        inventory.setItem(13, elite);
        inventory.setItem(14, supreme);
        inventory.setItem(15, acentra);
        inventory.setItem(18, non);
        inventory.setItem(26, helpgui);

        player.openInventory(inventory);
        return true;
    }



    private ItemStack createItem(Material material, int amount, String displayName, List<String> lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private List<String> createLore(FileConfiguration config, String loreKey) {
        List<String> lore = new ArrayList<>();
        for (String line : config.getStringList(loreKey)) {
            lore.add(CC.translate(line));
        }
        return lore;
    }
}
