package dev.acentra.disguise;

import dev.acentra.disguise.data.DataManager;

import dev.acentra.disguise.utils.chat.Console;
import dev.iiahmed.disguise.DisguiseManager;
import dev.iiahmed.disguise.DisguiseProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class DisguisePlugin extends JavaPlugin {

    public static final Logger console = Logger.getLogger("Minecraft");
    private static DisguiseAPI API;
    private static DisguisePlugin main;
    private final DisguiseProvider provider = DisguiseManager.getProvider();
    @Override
    public void onEnable() {
        main = this;
        API = new DisguiseAPI(this);
        Console.sendMessage("&9lDisguise Loaded successfully");
    }

    public static DisguisePlugin getPlugin() {
        return main;
    }

    public DisguiseProvider getProvider() {
        return provider;
    }

    public static DisguiseAPI api() {
        return API;
    }

    public static DataManager dataManager() {
        return api().getDataManager();
    }
}
