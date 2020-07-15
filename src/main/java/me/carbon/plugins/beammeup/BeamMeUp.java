package me.carbon.plugins.beammeup;

import me.carbon.plugins.beammeup.commands.BeamCommandManager;
import me.carbon.plugins.beammeup.locations.LocationManager;
import org.bukkit.plugin.java.JavaPlugin;

// TODO: Implement groups for locations
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
        this.saveDefaultConfig();
        this.locationFileName = this.getConfig().getString("location-file-name");
        this.lm = new LocationManager(this.getDataFolder(), this.locationFileName);

        this.getCommand("beam").setExecutor(new BeamCommandManager("beam", this));
        this.getLogger().info("beam-me-up enabled!");
    }

    public LocationManager getLocationManager() {
        return this.lm;
    }
}
