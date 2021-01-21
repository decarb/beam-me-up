package me.carbon.plugins.beammeup.commands.subcommands;

import me.carbon.plugins.beammeup.BeamMeUp;
import me.carbon.plugins.beammeup.locations.LocationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RenameSubCommand extends SubCommand {
    private final BeamMeUp pluginInstance;

    public RenameSubCommand(BeamMeUp pluginInstance) {
        super("beam.rename", true);
        this.pluginInstance = pluginInstance;
    }

    @Override
    public void onCommand(CommandSender commandSender, Command parentCommand, String alias, String[] args) {
        if (commandSender.hasPermission(getPermission())) {
            if (args.length == 2) {
                LocationManager lm = this.pluginInstance.getLocationManager();
                String name = args[0].toLowerCase();
                String rename = args[1].toLowerCase();

                if (lm.hasLocation(name)) {
                    if (!lm.hasLocation(rename)) {
                        if (lm.renameLocation(name, rename)) commandSender.sendMessage("Location renamed successfully");
                        else commandSender.sendMessage("Something went wrong with the location saver");
                    } else commandSender.sendMessage("Location " + rename + " already exists");
                } else commandSender.sendMessage("Location " + name + " does not exist");
            } else commandSender.sendMessage("Expected two arguments");
        } else commandSender.sendMessage("You do not have permission to use this command");
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        if (args.length == 1 && commandSender.hasPermission(getPermission())) {
            LocationManager lm = this.pluginInstance.getLocationManager();
            List<String> locations = lm.getLocationNames();

            return locations.stream()
                    .filter(s -> s.startsWith(args[0]))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
