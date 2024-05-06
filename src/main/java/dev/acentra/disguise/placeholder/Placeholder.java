package dev.acentra.disguise.placeholder;

import dev.acentra.disguise.DisguisePlugin;
import dev.acentra.disguise.data.DisguiseData;
import dev.acentra.disguise.utils.chat.CC;
import dev.acentra.disguise.utils.NameUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class Placeholder extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "ldisguise";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Acentra";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return Bukkit.getPluginManager().isPluginEnabled("lDisguise");
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String identifier) {
        if (player == null) return "";
        if (!player.isOnline()) return "Offline Player";

        DisguiseData data = DisguisePlugin.dataManager().getData(player.getPlayer().getUniqueId());

        switch (identifier) {
            case "name":
                return getPlayerName(player);
            case "rank":
                return getPlayerRank(player);
            case "suffix":
                return getPlayerSuffix(player);
            case "rank_change":
                return getRankChange(player, data);
        }

        return null;
    }

    private String getRankChange(OfflinePlayer player, DisguiseData data) {
        if (data.isDisguised()) {
            return "&7";
        } else {
            return getPlayerRank(player);
        }
    }

    private String getPlayerName(OfflinePlayer player) {
        return player.getPlayer().getName();
    }

    private String getPlayerRank(OfflinePlayer player) {
        DisguiseData data = DisguisePlugin.dataManager().getData(player.getPlayer().getUniqueId());

        if (data.isDisguised()) {
            return "Fake Rank";
        } else if (data.isHidingRank()) {
            return ChatColor.GRAY.toString();
        } else {

            return CC.translate(NameUtils.getPlayerRankPrefix(getSelectedRank(player)));
        }
    }

    private String getPlayerSuffix(OfflinePlayer player) {
        if (DisguisePlugin.dataManager().isDisguised(player.getPlayer().getUniqueId())) {
            return ChatColor.GRAY.toString();
        } else {
            return CC.translate(NameUtils.getPlayerSuffix(player.getUniqueId()));
        }
    }
    public static String getSelectedRank(OfflinePlayer player) {
        DisguiseData data = DisguisePlugin.dataManager().getData(player.getUniqueId());
        if (data != null) {
            return data.getSelectedRank();
        } else {
            return "";
        }
    }
}