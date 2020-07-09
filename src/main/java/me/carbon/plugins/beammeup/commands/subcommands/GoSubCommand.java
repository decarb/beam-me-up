package me.carbon.plugins.beammeup.commands.subcommands;

import me.carbon.plugins.beammeup.BeamMeUp;
import me.carbon.plugins.beammeup.LocationFileManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GoSubCommand extends SubCommand {
    private final String name;
    private final BeamMeUp pluginInstance;

    public GoSubCommand(String name, BeamMeUp pluginInstance) {
        this.name = name;
        this.pluginInstance = pluginInstance;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void onCommand(CommandSender commandSender, Command parentCommand, String alias, String[] args) {
        if (commandSender.hasPermission("beam.go")) {
            if (args.length == 1) {
                LocationFileManager lfm = new LocationFileManager(this.pluginInstance);
                String name = args[0].toLowerCase();

                if (lfm.hasLocation(name)) {
                    ((Player) commandSender).teleport(lfm.readLocation(name));
                    commandSender.sendMessage("You were teleported to " + name);
                } else commandSender.sendMessage("Location not found - Make sure that you typed the name correctly");
            } else commandSender.sendMessage("Expected one argument");
        } else commandSender.sendMessage("You do not have permission to use this command");
    }

    // TODO: Abstract this out maybe? Too stupid to think about it now.
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            LocationFileManager lfm = new LocationFileManager(this.pluginInstance);
            List<String> locations = lfm.readLocationNames();

            return locations.stream().filter(s -> s.startsWith(args[0])).sorted().collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
