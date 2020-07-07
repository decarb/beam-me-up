package me.carbon.plugins.beammeup;

import org.bukkit.Location;
import org.bukkit.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

// TODO: Update location saving so that you don't have to overwrite file each time - Not super important
public class LocationFileManager {
    private final BeamMeUp pluginInstance;

    public LocationFileManager(BeamMeUp pluginInstance) {
        this.pluginInstance = pluginInstance;
    }

    public Map<String, Location> readLocations() {
        File f = new File(this.pluginInstance.getDataFolder(), this.pluginInstance.getLocationFileName());
        Map<String, Location> out = new HashMap<>();

        if (f.exists()) {
            try {
                JSONParser parser = new JSONParser();
                FileReader fr = new FileReader(f);

                JSONArray array = (JSONArray) parser.parse(fr);
                for (Object o : array) {
                    JSONObject json = (JSONObject) o;

                    World world = this.pluginInstance.getServer().getWorld((String) json.get("world_uuid"));
                    double x = (double) json.get("x");
                    double y = (double) json.get("y");
                    double z = (double) json.get("z");

                    Location location = new Location(world, x, y, z);
                    out.put((String) json.get("name"), location);
                }

                fr.close();
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }

        return out;
    }

    public boolean saveLocation(String name, Location location) {
        Map<String, Location> locations = this.readLocations();
        if (locations.containsKey(name)) locations.replace(name, location); else locations.put(name, location);

        return this.writeLocations(locations);
    }

    private boolean writeLocations(Map<String, Location> locations) {
        File dataFolder = this.pluginInstance.getDataFolder();
        File f = new File(dataFolder, this.pluginInstance.getLocationFileName());

        try {
            if (!dataFolder.exists()) dataFolder.mkdirs();
            FileWriter fw = new FileWriter(f, false);

            JSONArray writeArray = new JSONArray();
            for (Map.Entry<String, Location> entry : locations.entrySet()) {
                JSONObject o = new JSONObject();
                Location location = entry.getValue();
                o.put("name", entry.getKey());
                o.put("x", location.getX());
                o.put("y", location.getY());
                o.put("z", location.getZ());
                o.put("world_uuid", location.getWorld().getUID().toString());
                writeArray.add(o);
            }

            fw.write(writeArray.toJSONString());
            fw.flush();
            fw.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
    }
}
