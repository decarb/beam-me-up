package me.carbon.plugins.beammeup.locations;

import me.carbon.plugins.beammeup.BeamMeUp;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationManager {
    private final Map<String, Location> locations;
    private final LocationFileManager lfm;

    public LocationManager(BeamMeUp pluginInstance) {
        this.lfm = new LocationFileManager(pluginInstance);
        this.locations = lfm.readLocations();
    }

    public boolean saveLocation(String name, Location location) {
        // TODO: Include yaw and pitch in the location saving process
        location.setYaw(0);
        location.setPitch(0);

        if (this.lfm.saveLocation(name, location)) {
            this.locations.put(name, location);
            return true;
        } else return false;
    }

    public boolean removeLocation(String name) {
        if (this.lfm.removeLocation(name)) {
            this.locations.remove(name);
            return true;
        } else return false;
    }

    public Location getLocation(String name) {
        return this.locations.get(name);
    }

    public List<String> getLocationNames() {
        return new ArrayList<>(this.locations.keySet());
    }

    public boolean hasLocation(String name) {
        return this.locations.containsKey(name);
    }
}
