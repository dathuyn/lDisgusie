package dev.acentra.disguise.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class DisguiseData {
    private Player player;

    private UUID uuid;
    private String realName;

    @Setter
    private String disguisedName, nickName, selectedRank;

    @Setter
    private boolean disguised, await, nicked, hidingRank;

    public DisguiseData(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();

        this.realName = player.getName();
        this.disguisedName = this.realName;
        this.selectedRank = "";
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();

        this.realName = player.getName();
    }
}