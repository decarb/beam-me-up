package me.carbon.plugins.beammeup.commands.subcommands;

import me.carbon.plugins.beammeup.BeamMeUp;
import me.carbon.plugins.beammeup.LocationFileManager;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSubCommand extends SubCommand {
    private final String name;
    private final BeamMeUp pluginInstance;

    public SetSubCommand(String name, BeamMeUp pluginInstance) {
        this.name = name;
        this.pluginInstance = pluginInstance;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] strings) {
        if (commandSender.hasPermission("beam.set")) {
            if (strings.length == 1) {
                String name = strings[0].toLowerCase();
                Location here = ((Player) commandSender).getLocation();
                LocationFileManager lfm = new LocationFileManager(this.pluginInstance);

                if (lfm.saveLocation(name, here)) commandSender.sendMessage("Locations updated successfully");
                else commandSender.sendMessage("Something went wrong with the location saver - Please report an issue");
            } else commandSender.sendMessage("Expected one argument");
        } else commandSender.sendMessage("You do not have permission to use this command");
    }
}
