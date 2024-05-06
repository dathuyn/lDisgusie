package dev.acentra.disguise.utils.menu;


import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Menu implements InventoryHolder {
    private final Map<Integer, Button> items = new HashMap<>();

    protected Inventory inventory;

    private final Player player;

    private final String title;
    private final int slots;
    private final Consumer<Menu> setMenuItems;

    @Getter
    private final Consumer<InventoryClickEvent> onClick;

    Menu(Inventory inventory, Player player, String title, int slots, Consumer<Menu> setMenuItems, Consumer<InventoryClickEvent> onClick) {
        this.inventory = inventory;
        this.player = player;
        this.title = title;
        this.slots = slots;
        this.setMenuItems = setMenuItems;
        this.onClick = onClick;
    }

    public static MenuBuilder builder() {
        return new MenuBuilder();
    }

    public void open() {
        this.inventory = Bukkit.createInventory(this, slots, title);

        this.setMenuItems.accept(this);

        this.player.openInventory(inventory);
    }

    public void handleClick(InventoryClickEvent event) {
        if (this.items.containsKey(event.getSlot())) {
            this.items.get(event.getSlot()).getOnClick().accept((Player) event.getWhoClicked());
        }

        if (this.onClick != null) onClick.accept(event);
    }

    public void addItemNoInteract(int slot) {
        this.inventory.setItem(slot, Button.PLACEHOLDER.toItemStack());
    }

    public void addItem(int slot, Button button) {
        this.items.put(slot, button);
        this.inventory.setItem(slot, button.toItemStack());
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public static class MenuBuilder {
        private Inventory inventory;
        private Player player;
        private String title;
        private int slots;
        private Consumer<Menu> setMenuItems;
        private Consumer<InventoryClickEvent> onClick;

        MenuBuilder() {
        }

        public MenuBuilder inventory(Inventory inventory) {
            this.inventory = inventory;
            return this;
        }

        public MenuBuilder player(Player player) {
            this.player = player;
            return this;
        }

        public MenuBuilder title(String title) {
            this.title = title;
            return this;
        }

        public MenuBuilder slots(int slots) {
            this.slots = slots;
            return this;
        }

        public MenuBuilder setMenuItems(Consumer<Menu> setMenuItems) {
            this.setMenuItems = setMenuItems;
            return this;
        }

        public MenuBuilder onClick(Consumer<InventoryClickEvent> onClick) {
            this.onClick = onClick;
            return this;
        }

        public Menu build() {
            return new Menu(this.inventory, this.player, this.title, this.slots, this.setMenuItems, this.onClick);
        }
    }
}