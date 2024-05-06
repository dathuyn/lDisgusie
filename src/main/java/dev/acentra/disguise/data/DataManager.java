package dev.acentra.disguise.data;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class DataManager {
    @Getter
    private final Map<UUID, DisguiseData> disguised = new HashMap<>();


    /**
     * Register disguise data
     * @param player - Player
     */
    public void registerData(Player player) {
        disguised.put(player.getUniqueId(), new DisguiseData(player));
    }


    /**
     * Unregister disguise data
     * @param player Player
     */
    public void unregisterData(Player player) {
        disguised.remove(player.getUniqueId());
    }

    /**
     * Return data of UUID
     *
     * @param uuid - UUID
     * @return Disguise Data
     */
    public DisguiseData getData(UUID uuid) {
        return disguised.get(uuid);
    }

    /**
     * Find data by searching disguise name
     *
     * @param name - Name
     * @return Disguise Data
     */
    public DisguiseData getDataByName(String name) {
        Optional<DisguiseData> match = disguised.values().stream().filter(data -> {
            return data.getDisguisedName().equalsIgnoreCase(name) || data.getRealName().equalsIgnoreCase(name);
        }).findFirst();

        return match.orElse(null);
    }

    /**
     * Return if data is disguised
     *
     * @param uuid - UUID
     * @return Disguised or not
     */
    public boolean isDisguised(UUID uuid) {
        return getData(uuid).isDisguised();
    }
}