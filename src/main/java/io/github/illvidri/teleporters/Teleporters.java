package io.github.illvidri.teleporters;

import org.bukkit.plugin.java.JavaPlugin;

public class Teleporters extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }
}
