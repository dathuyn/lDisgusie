package dev.acentra.disguise;

import dev.acentra.disguise.commands.BaseCommand;
import dev.acentra.disguise.commands.impl.*;
import dev.acentra.disguise.data.DataManager;
import dev.acentra.disguise.listener.*;
import dev.acentra.disguise.placeholder.Placeholder;
import dev.iiahmed.disguise.DisguiseManager;
import lombok.Getter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class DisguiseAPI {
    @Getter
    private final DataManager dataManager = new DataManager();

    @Getter
    private final Set<UUID> alerted = new HashSet<>();

    @Getter
    private final Set<String> blacklist = new HashSet<>();

    @Getter
    private final List<BaseCommand> commands = new ArrayList<>();

    private final List<String> blacklistPaths = Arrays.asList("staff", "media", "forbiddenNames", "prefixes");

    private long reloadInterval =  60;
    private int reloadTask = 0;

    public DisguiseAPI(final DisguisePlugin plugin) {

        // Hooking to ModernDisguise
        DisguiseManager.setPlugin(plugin);


        // Register Placeholder \\
        new Placeholder().register();

        // Register commands \\

        registerCommand(plugin, "disguise", new DisguiseCommand(plugin.getConfig()));
        registerCommand(plugin, "undisguise", new DisguiseRemoveCommand());
        registerCommand(plugin, "disguiselist", new DisguiseListCommand(plugin.getConfig()));
        registerCommand(plugin, "resetdisguise", new DisguiseResetCommand(plugin.getConfig()));
        registerCommand(plugin, "revealdisguise", new DisguiseRevealCommand(plugin.getConfig()));

        registerCommand(plugin, "nick", new NickCommand(plugin.getConfig()));
        registerCommand(plugin, "unnick", new NickRemoveCommand(plugin.getConfig()));
        registerCommand(plugin, "nicklist", new NickListCommand(plugin.getConfig()));
        registerCommand(plugin, "resetnick", new NickResetCommand(plugin.getConfig()));
        registerCommand(plugin, "revealnick", new NickRevealCommand(plugin.getConfig()));

        registerCommand(plugin, "rankchange", new RankCommandChange(plugin.getConfig()));
        registerCommand(plugin, "disgusiemenu", new DisguiseMenuCore(plugin.getConfig()));

        this.commands.forEach(command -> {
            command.setApi(this);
            command.setDataManager(dataManager);
        });

        // Register Listeners \\
        plugin.getServer().getPluginManager().registerEvents(new JoinListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MenuListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new DisguiseListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new Menurankchange(plugin, plugin.getConfig()), plugin);
        plugin.getServer().getPluginManager().registerEvents(new DisguiseListenerCore(), plugin);

        // Register config \\
        plugin.saveDefaultConfig();

        reloadConfig(plugin);

        reloadInterval = Math.max(plugin.getConfig().getLong("reload-interval", 60), 1);

        buildReloadTask(plugin);

        (new BukkitRunnable() {
            @Override
            public void run() {
                System.gc();
            }
        }).runTaskTimerAsynchronously(plugin, 200L, 6000L);

    }

    private void buildReloadTask(final DisguisePlugin plugin) {
        reloadTask = (new BukkitRunnable() {
            @Override
            public void run() {
                reloadConfig(plugin);
            }
        }).runTaskTimerAsynchronously(plugin, 200L, reloadInterval * 20L).getTaskId();
    }

    public void reloadConfig(final DisguisePlugin plugin) {
        blacklist.clear();
        FileConfiguration configuration = plugin.getConfig();
        blacklistPaths.forEach(path -> {
            blacklist.addAll(configuration.getStringList(path)
                    .stream().map(String::toLowerCase).collect(Collectors.toList()));
        });
        long interval = configuration.getLong("reload-interval", 60);
        if(reloadInterval != interval) {
            Bukkit.getScheduler().cancelTask(reloadTask);
            reloadInterval = Math.max(interval, 1);
            buildReloadTask(plugin);
        }
    }

    public boolean isDisguised(Player player) {
        return dataManager.isDisguised(player.getUniqueId());
    }

    public void alert(String message) {
        for (UUID uuid : alerted) {
            Player listener = Bukkit.getPlayer(uuid);
            if (listener == null) continue;
            listener.spigot().sendMessage(new TextComponent(message));
        }
    }

    public void registerCommand(DisguisePlugin plugin, String name, BaseCommand command) {
        this.commands.add(command);
        plugin.getCommand(name).setExecutor(command);
    }
}
