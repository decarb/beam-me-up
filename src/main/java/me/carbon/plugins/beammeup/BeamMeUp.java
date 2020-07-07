package me.carbon.plugins.beammeup;

import me.carbon.plugins.beammeup.commands.BeamCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BeamMeUp extends JavaPlugin {
    @Override
    public void onDisable() {
        this.getLogger().info("Beam me up disabled!");
    }

    @Override
    public void onEnable() {
        this.getCommand("beam").setExecutor(new BeamCommandManager(this));
        this.getLogger().info("Beam me up enabled!");
    }
}
