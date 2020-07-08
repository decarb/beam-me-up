package me.carbon.plugins.beammeup.commands.subcommands;

import me.carbon.plugins.beammeup.BeamMeUp;
import me.carbon.plugins.beammeup.LocationFileManager;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class RemoveSubCommand extends SubCommand {
    private final String name;
    private final BeamMeUp pluginInstance;

    public RemoveSubCommand(String name, BeamMeUp pluginInstance) {
        this.name = name;
        this.pluginInstance = pluginInstance;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] strings) {
        if (commandSender.hasPermission("beam.remove")) {
            if (strings.length == 1) {
                LocationFileManager lfm = new LocationFileManager(this.pluginInstance);
                Map<String, Location> locations = lfm.readLocations();
                String name = strings[0].toLowerCase();

                if (locations.containsKey(name)) {
                    if (lfm.removeLocation(name)) commandSender.sendMessage("Location " + name + " removed successfully");
                    else commandSender.sendMessage("Something went wrong with the location saver - Please report an issue");
                } else commandSender.sendMessage("Location " + name + " does not exist");
            } else commandSender.sendMessage("Expected one argument");
        } else commandSender.sendMessage("You do not have permission to use this command");
    }
}
