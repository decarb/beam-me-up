package me.carbon.plugins.beammeup.commands.subcommands;

import me.carbon.plugins.beammeup.BeamMeUp;
import me.carbon.plugins.beammeup.LocationFileManager;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class ListSubCommand extends SubCommand {
    private final String name;
    private final BeamMeUp pluginInstance;

    public ListSubCommand(String name, BeamMeUp pluginInstance) {
        this.name = name;
        this.pluginInstance = pluginInstance;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] strings) {
        if (commandSender.hasPermission("beam.list")) {
            if (strings.length == 0) {
                LocationFileManager lfm = new LocationFileManager(this.pluginInstance);
                List<String> locationList = lfm.readLocations()
                        .keySet()
                        .stream()
                        .sorted()
                        .collect(Collectors.toList());

                StringBuilder sb = new StringBuilder().append("Locations:");
                for (String location : locationList) sb.append("\n- ").append(location);
                commandSender.sendMessage(sb.toString());
            } else commandSender.sendMessage("Expected zero arguments");
        } else commandSender.sendMessage("You do not have permission to use this command");
    }
}
