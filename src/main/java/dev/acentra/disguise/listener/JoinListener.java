package dev.acentra.disguise.listener;

import dev.acentra.disguise.DisguiseAPI;
import dev.acentra.disguise.DisguisePlugin;
import dev.acentra.disguise.data.DisguiseData;
import dev.acentra.disguise.event.DisguiseAction;
import dev.acentra.disguise.event.DisguiseEvent;
import dev.acentra.disguise.utils.permissions.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinListener implements org.bukkit.event.Listener {
    private final DisguiseAPI API;

    public JoinListener(DisguiseAPI API) {
        this.API = API;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        // Register data
        if (!API.getDataManager().getDisguised().containsKey(uuid)) {
            API.getDataManager().registerData(player);
        }
        DisguiseData data = API.getDataManager().getData(uuid);

        // Give alerts to those who have the permission
        if (player.hasPermission(Permissions.ALERTS) || player.isOp()) {
            API.getAlerted().add(uuid);
        }

        if (data != null) data.setPlayer(player);

        // Disguise as soon as you join
        if (data != null && data.isAwait()) {
            new DisguiseEvent(DisguiseAction.SET, event.getPlayer(), data.getDisguisedName(), false).call();
            data.setAwait(false);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        // Remove anyone who is listening to alerts
        API.getAlerted().remove(uuid);

        DisguiseData data = API.getDataManager().getData(uuid);

        if (data == null) return;

        if (data.isDisguised()) {
            data.setDisguised(false);
            data.setAwait(true);
        }

        data.setHidingRank(false);

        if (data.isNicked()) data.setNicked(false);
    }
}
