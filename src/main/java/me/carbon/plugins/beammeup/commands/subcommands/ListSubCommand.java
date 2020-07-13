package me.carbon.plugins.beammeup.commands.subcommands;

import me.carbon.plugins.beammeup.BeamMeUp;
import me.carbon.plugins.beammeup.locations.LocationFileManager;
import me.carbon.plugins.beammeup.locations.LocationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListSubCommand extends SubCommand {
    private final BeamMeUp pluginInstance;

    public ListSubCommand(BeamMeUp pluginInstance) {
        super("beam.list", true);
        this.pluginInstance = pluginInstance;
    }

    @Override
    public void onCommand(CommandSender commandSender, Command parentCommand, String alias, String[] args) {
        if (commandSender.hasPermission(this.permission)) {
            if (args.length == 0) {
                LocationManager lm = this.pluginInstance.getLocationManager();
                List<String> locationList = lm.getLocationNames();
                Collections.sort(locationList);

                StringBuilder sb = new StringBuilder().append("Locations:");
                for (String location : locationList) sb.append("\n- ").append(location);
                commandSender.sendMessage(sb.toString());
            } else commandSender.sendMessage("Expected zero arguments");
        } else commandSender.sendMessage("You do not have permission to use this command");
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
}
