package ru.kernel;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.kernel.events.onDie;
import ru.kernel.events.onJoin;

import java.util.HashMap;
import java.util.UUID;

public final class DieDelay extends JavaPlugin {

    public FileConfiguration config;
    public HashMap<UUID, Boolean> startInfo = new HashMap<>();
    public HashMap<UUID, String> timeDis = new HashMap<>();

    @Override
    public void onEnable() {

        config = getConfig();
        saveDefaultConfig();

        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new onDie(), this);
        this.getServer().getPluginManager().registerEvents(new onJoin(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
