package io.github.illvidri.teleporters;

import io.github.illvidri.teleporters.listeners.BlockChecker;
import org.bukkit.plugin.java.JavaPlugin;

public class Teleporters extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("> Loaded.");
        getServer().getPluginManager().registerEvents(new BlockChecker(), this);
    }
}
