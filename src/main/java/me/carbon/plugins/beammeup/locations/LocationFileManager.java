package me.carbon.plugins.beammeup.locations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import me.carbon.plugins.beammeup.models.LocationJson;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LocationFileManager {
    private final File dataFolder;
    private final String fileName;

    public LocationFileManager(File dataFolder, String fileName) {
        this.dataFolder = dataFolder;
        this.fileName = fileName;
    }

    public boolean saveLocation(String name, Location location) {
        Map<String, Location> locations = this.readLocations();
        locations.put(name, location);
        return this.writeLocations(locations);
    }

    public boolean removeLocation(String name) {
        Map<String, Location> locations = this.readLocations();
        locations.remove(name);
        return this.writeLocations(locations);
    }

    // TODO: Make this whole class more robust to errors
    public boolean renameLocation(String name, String rename) {
        Map<String, Location> locations = this.readLocations();
        Location location = locations.get(name);
        locations.remove(name);
        locations.put(rename, location);
        return this.writeLocations(locations);
    }

    public Map<String, Location> readLocations() {
        File f = new File(this.dataFolder, this.fileName);

        if (f.exists()) {
            try {
                Type locationsType = new TypeToken<Map<String, LocationJson>>() {
                }.getType();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonReader jr = new JsonReader(new FileReader(f));

                Map<String, LocationJson> locations = gson.fromJson(jr, locationsType);

                Map<String, Location> out = locations.entrySet().stream().collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                l -> {
                                    LocationJson value = l.getValue();
                                    return new Location(Bukkit.getWorld(value.world_uuid), value.x, value.y, value.z);
                                }
                        )
                );

                jr.close();

                return out;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new HashMap<>();
    }

    private boolean writeLocations(Map<String, Location> locations) {
        File dataFolder = this.dataFolder;
        File f = new File(dataFolder, this.fileName);

        try {
            if (!dataFolder.exists()) dataFolder.mkdirs();
            FileWriter fw = new FileWriter(f, false);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Map<String, LocationJson> locationJson = locations.entrySet().stream().collect(
                    Collectors.toMap(
                            Map.Entry::getKey,
                            l -> {
                                Location value = l.getValue();
                                return new LocationJson(
                                        value.getWorld().getUID(),
                                        value.getX(),
                                        value.getY(),
                                        value.getZ()
                                );
                            }
                    )
            );

            fw.write(gson.toJson(locationJson));
            fw.flush();
            fw.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
