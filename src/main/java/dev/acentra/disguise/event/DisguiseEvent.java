package dev.acentra.disguise.event;

import dev.acentra.disguise.DisguisePlugin;
import dev.acentra.disguise.data.DisguiseData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@RequiredArgsConstructor
public class DisguiseEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final DisguiseAction action;
    private final DisguiseData disguiseData;

    private final Player player;

    private String disguiseName;
    private boolean random;


    public DisguiseEvent(DisguiseAction action, Player player) {
        this.action = action;
        this.player = player;
        this.disguiseData = DisguisePlugin.api().getDataManager().getData(player.getUniqueId());
    }

    public DisguiseEvent(DisguiseAction action, Player player, String disguiseName, boolean random) {
        this.action = action;
        this.player = player;
        this.disguiseData = DisguisePlugin.api().getDataManager().getData(player.getUniqueId());
        this.disguiseName = disguiseName;
        this.random = random;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public void call() {
        Bukkit.getPluginManager().callEvent(this);
    }
}
