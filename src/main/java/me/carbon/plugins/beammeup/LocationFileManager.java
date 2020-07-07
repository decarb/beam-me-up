package me.carbon.plugins.beammeup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.bukkit.Location;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class LocationFileManager {
    private class LocationJson {
        private final String name;
        private final UUID world_uuid;
        private final double x;
        private final double y;
        private final double z;

        public LocationJson(String name, UUID worldUuid, double x, double y, double z) {
            this.name = name;
            this.world_uuid = worldUuid;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private final BeamMeUp pluginInstance;

    public LocationFileManager(BeamMeUp pluginInstance) {
        this.pluginInstance = pluginInstance;
    }

    public Map<String, Location> readLocations() {
        File f = new File(this.pluginInstance.getDataFolder(), this.pluginInstance.getLocationFileName());
        Map<String, Location> out = new HashMap<>();

        if (f.exists()) {
            try {
                Type locationsType = new TypeToken<ArrayList<LocationJson>>() {
                }.getType();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonReader jr = new JsonReader(new FileReader(f));

                List<LocationJson> locations = gson.fromJson(jr, locationsType);

                for (LocationJson location : locations) {
                    Location l = new Location(
                            this.pluginInstance.getServer().getWorld(location.world_uuid),
                            location.x,
                            location.y,
                            location.z
                    );

                    out.put(location.name, l);
                }

                jr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return out;
    }

    public boolean saveLocation(String name, Location location) {
        Map<String, Location> locations = this.readLocations();
        if (locations.containsKey(name)) locations.replace(name, location);
        else locations.put(name, location);

        return this.writeLocations(locations);
    }

    private boolean writeLocations(Map<String, Location> locations) {
        File dataFolder = this.pluginInstance.getDataFolder();
        File f = new File(dataFolder, this.pluginInstance.getLocationFileName());

        try {
            if (!dataFolder.exists()) dataFolder.mkdirs();
            FileWriter fw = new FileWriter(f, false);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            List<LocationJson> locationJson = new ArrayList<>();
            for (Map.Entry<String, Location> entry : locations.entrySet()) {
                locationJson.add(new LocationJson(
                        entry.getKey(),
                        entry.getValue().getWorld().getUID(),
                        entry.getValue().getX(),
                        entry.getValue().getY(),
                        entry.getValue().getZ()
                ));
            }

            fw.write(gson.toJson(locationJson));
            fw.flush();
            fw.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
    }
}
