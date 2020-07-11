package me.carbon.plugins.beammeup.commands.subcommands;

import me.carbon.plugins.beammeup.BeamMeUp;
import me.carbon.plugins.beammeup.LocationFileManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SetSubCommand extends SubCommand {
    private final String name;
    private final BeamMeUp pluginInstance;

    public SetSubCommand(String name, BeamMeUp pluginInstance) {
        this.name = name;
        this.pluginInstance = pluginInstance;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void onCommand(CommandSender commandSender, Command parentCommand, String alias, String[] args) {
        if (commandSender.hasPermission("beam.set")) {
            if (args.length == 1) {
                String name = args[0].toLowerCase();
                Location here = ((Player) commandSender).getLocation();
                LocationFileManager lfm = new LocationFileManager(this.pluginInstance);

                if (lfm.saveLocation(name, here)) commandSender.sendMessage("Locations updated successfully");
                else commandSender.sendMessage("Something went wrong with the location saver - Please report an issue");
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
