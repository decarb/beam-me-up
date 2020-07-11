package me.carbon.plugins.beammeup;

import me.carbon.plugins.beammeup.commands.BeamCommandManager;
import me.carbon.plugins.beammeup.locations.LocationManager;
import org.bukkit.plugin.java.JavaPlugin;

// TODO: Add some documentation you fool!
public class BeamMeUp extends JavaPlugin {
    private String locationFileName;
    private LocationManager lm;

    @Override
    public void onDisable() {
        this.getLogger().info("beam-me-up disabled!");
    }

    @Override
    public void onEnable() {
        this.locationFileName = "locations.json"; // TODO: Change this so that the user can choose a file
        this.lm = new LocationManager(this);

        this.getCommand("beam").setExecutor(new BeamCommandManager(this));
        this.getLogger().info("beam-me-up enabled!");
    }

    public String getLocationFileName() {
        return this.locationFileName;
    }

    public LocationManager getLocationManager() {
        return this.lm;
    }
}
