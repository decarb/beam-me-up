package me.carbon.plugins.beammeup.commands.subcommands;

import me.carbon.plugins.beammeup.BeamMeUp;
import me.carbon.plugins.beammeup.locations.LocationFileManager;
import me.carbon.plugins.beammeup.locations.LocationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveSubCommand extends SubCommand {
    private final String name;
    private final BeamMeUp pluginInstance;
    private final String permission = "beam.remove";

    public RemoveSubCommand(String name, BeamMeUp pluginInstance) {
        this.name = name;
        this.pluginInstance = pluginInstance;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPermission() {
        return this.permission;
    }

    @Override
    public void onCommand(CommandSender commandSender, Command parentCommand, String alias, String[] args) {
        if (commandSender.hasPermission(this.permission)) {
            if (args.length == 1) {
                LocationManager lm = this.pluginInstance.getLocationManager();
                String name = args[0].toLowerCase();

                if (lm.hasLocation(name)) {
                    if (lm.removeLocation(name)) commandSender.sendMessage("Location " + name + " removed successfully");
                    else commandSender.sendMessage("Something went wrong with the location saver");
                } else commandSender.sendMessage("Location " + name + " does not exist");
            } else commandSender.sendMessage("Expected one argument");
        } else commandSender.sendMessage("You do not have permission to use this command");
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            LocationManager lm = this.pluginInstance.getLocationManager();
            List<String> locations = lm.getLocationNames();

            return locations.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
