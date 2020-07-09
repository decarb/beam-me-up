package me.carbon.plugins.beammeup.commands.subcommands;

import me.carbon.plugins.beammeup.BeamMeUp;
import me.carbon.plugins.beammeup.LocationFileManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            if (strings.length == 1) {
                LocationFileManager lfm = new LocationFileManager(this.pluginInstance);
                String name = strings[0].toLowerCase();

                if (lfm.hasLocation(name)) {
                    ((Player) commandSender).teleport(lfm.readLocation(name));
                    commandSender.sendMessage("You were teleported to " + name);
                } else commandSender.sendMessage("Location not found - Make sure that you typed the name correctly");
            } else commandSender.sendMessage("Expected one argument");
        } else commandSender.sendMessage("You do not have permission to use this command");
    }
}
