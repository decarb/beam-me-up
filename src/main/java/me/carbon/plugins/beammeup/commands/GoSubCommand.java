package me.carbon.plugins.beammeup.commands;

import me.carbon.plugins.beammeup.BeamMeUp;
import me.carbon.plugins.beammeup.LocationFileManager;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class GoSubCommand extends SubCommand {
    private final String name;
    private final BeamMeUp pluginInstance;

    public GoSubCommand(String name, BeamMeUp pluginInstance) {
        this.name = name;
        this.pluginInstance = pluginInstance;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] strings) {
        if (commandSender.hasPermission("beam.go")) {
            if (strings.length != 1) commandSender.sendMessage("Expected only one argument");
            else {
                LocationFileManager lfm = new LocationFileManager(this.pluginInstance);
                Map<String, Location> locations = lfm.readLocations();
                String name = strings[0].toLowerCase();

                if (locations.containsKey(name)) {
                    ((Player) commandSender).teleport(locations.get(name));
                    commandSender.sendMessage("You were teleported to " + name);
                } else {
                    commandSender.sendMessage("Location not found - Make sure that you typed the name correctly");
                }
            }
        } else {
            commandSender.sendMessage("You do not have permission to use this command");
        }
    }
}
