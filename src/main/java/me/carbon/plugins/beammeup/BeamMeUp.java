package me.carbon.plugins.beammeup;

import me.carbon.plugins.beammeup.commands.BeamCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

// TODO: Use local variable to store location names - Might take long
// TODO: Add some documentation you fool!
public class BeamMeUp extends JavaPlugin {
    private final String locationFileName = "locations.json";

    @Override
    public void onDisable() {
        this.getLogger().info("beam-me-up disabled!");
    }

    @Override
    public void onEnable() {
        this.getCommand("beam").setExecutor(new BeamCommandManager(this));
        this.getLogger().info("beam-me-up enabled!");
    }

    public String getLocationFileName() {
        return this.locationFileName;
    }
}
