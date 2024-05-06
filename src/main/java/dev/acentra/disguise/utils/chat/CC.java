package dev.acentra.disguise.utils.chat;

import org.bukkit.ChatColor;

public class CC {
    public static final ChatColor BLUE = ChatColor.BLUE;
    public static final ChatColor AQUA = ChatColor.AQUA;
    public static final ChatColor GREEN = ChatColor.GREEN;
    public static final ChatColor RED = ChatColor.RED;
    public static final ChatColor WHITE = ChatColor.WHITE;
    public static final ChatColor YELLOW = ChatColor.YELLOW;
    public static final ChatColor LIGHT_PURPLE = ChatColor.LIGHT_PURPLE;
    public static final ChatColor GRAY = ChatColor.GRAY;
    public static final ChatColor DARK_GRAY = ChatColor.DARK_GRAY;
    public static final ChatColor GOLD = ChatColor.GOLD;
    public static final ChatColor DARK_PURPLE = ChatColor.DARK_PURPLE;
    public static final ChatColor DARK_RED = ChatColor.DARK_RED;
    public static final ChatColor DARK_AQUA = ChatColor.DARK_AQUA;
    public static final ChatColor DARK_GREEN = ChatColor.DARK_GREEN;
    public static final ChatColor DARK_BLUE = ChatColor.DARK_BLUE;
    public static final ChatColor BLACK = ChatColor.BLACK;

    public static String translate(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static String strip(String in) {
        // Remove paranthesis, brackets, and other unnecessary characters.
        in = in.replaceAll("[^A-Za-z0-9]", " ");

        return ChatColor.stripColor(in);
    }
}
