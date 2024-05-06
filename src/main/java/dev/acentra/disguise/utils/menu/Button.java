package dev.acentra.disguise.utils.menu;


import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class Button {
    private final Material material;
    private final Consumer<Player> onClick;
    private final List<String> lore;
    private final String displayName;

    private final int amount;
    private final Byte type;

    public static Button PLACEHOLDER = Button.builder().material(Material.STAINED_GLASS_PANE).build();

    Button(Material material, Consumer<Player> onClick, List<String> lore, String displayName, int amount, Byte type) {
        this.material = material;
        this.onClick = onClick;
        this.lore = lore;
        this.displayName = displayName;
        this.amount = amount;
        this.type = type;
    }

    public static ButtonBuilder builder() {
        return new ButtonBuilder();
    }

    public ItemStack toItemStack() {
        ItemStack item;

        if (type == null) {
            item = new ItemStack(this.material, this.amount);
        } else {
            item = new ItemStack(this.material, this.amount, this.type);
        }

        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.displayName));

        if (this.lore != null) {
            List<String> lore = new ArrayList<>();

            for (String temp : this.lore) lore.add(ChatColor.translateAlternateColorCodes('&', temp));

            itemMeta.setLore(lore);
        }

        item.setItemMeta(itemMeta);

        return item;
    }

    public static class ButtonBuilder {
        private Material material;
        private Consumer<Player> onClick;
        private List<String> lore;
        private String displayName;
        private int amount;
        private Byte type;

        ButtonBuilder() {
        }

        public ButtonBuilder material(Material material) {
            this.material = material;
            return this;
        }

        public ButtonBuilder onClick(Consumer<Player> onClick) {
            this.onClick = onClick;
            return this;
        }

        public ButtonBuilder lore(List<String> lore) {
            this.lore = lore;
            return this;
        }

        public ButtonBuilder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public ButtonBuilder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public ButtonBuilder type(Byte type) {
            this.type = type;
            return this;
        }

        public Button build() {
            return new Button(this.material, this.onClick, this.lore, this.displayName, this.amount, this.type);
        }
    }
}
