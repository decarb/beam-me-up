package me.carbon.plugins.beammeup.locations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

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

    public Map<String, Location> readLocations() {
        File f = new File(this.dataFolder, this.fileName);

        if (f.exists()) {
            try {
                Type locationsType = new TypeToken<ArrayList<LocationJson>>() {
                }.getType();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonReader jr = new JsonReader(new FileReader(f));

                List<LocationJson> locations = gson.fromJson(jr, locationsType);

                Map<String, Location> out = locations.stream().collect(
                        Collectors.toMap(
                                l -> l.name,
                                l -> new Location(Bukkit.getWorld(l.world_uuid), l.x, l.y, l.z)
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

            List<LocationJson> locationJson = locations.entrySet().stream().map(e ->
                    new LocationJson(
                            e.getKey(),
                            e.getValue().getWorld().getUID(),
                            e.getValue().getX(),
                            e.getValue().getY(),
                            e.getValue().getZ()
                    )
            ).collect(Collectors.toList());

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
