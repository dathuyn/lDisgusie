package dev.acentra.disguise.utils.chat;

import static org.bukkit.Bukkit.getServer;

public class Console {
    public static void sendMessage(String m) {
        getServer().getConsoleSender().sendMessage(CC.translate(m));
    }
}
