package me.carbon.plugins.beammeup.commands.subcommands;

import me.carbon.plugins.beammeup.BeamMeUp;
import me.carbon.plugins.beammeup.LocationFileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveSubCommand extends SubCommand {
    private final String name;
    private final BeamMeUp pluginInstance;

    public RemoveSubCommand(String name, BeamMeUp pluginInstance) {
        this.name = name;
        this.pluginInstance = pluginInstance;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void onCommand(CommandSender commandSender, Command parentCommand, String alias, String[] args) {
        if (commandSender.hasPermission("beam.remove")) {
            if (args.length == 1) {
                LocationFileManager lfm = new LocationFileManager(this.pluginInstance);
                String name = args[0].toLowerCase();

                if (lfm.hasLocation(name)) {
                    if (lfm.removeLocation(name)) commandSender.sendMessage("Location " + name + " removed successfully");
                    else commandSender.sendMessage("Something went wrong with the location saver - Please report an issue");
                } else commandSender.sendMessage("Location " + name + " does not exist");
            } else commandSender.sendMessage("Expected one argument");
        } else commandSender.sendMessage("You do not have permission to use this command");
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            LocationFileManager lfm = new LocationFileManager(this.pluginInstance);
            List<String> locations = lfm.readLocationNames();

            return locations.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
