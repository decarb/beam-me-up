package me.carbon.plugins.beammeup.commands;

import me.carbon.plugins.beammeup.BeamMeUp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class BeamCommandManager implements CommandExecutor {
    private final BeamMeUp pluginInstance;
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    // TODO: Add commands for "list" and "remove"
    public BeamCommandManager(BeamMeUp pluginInstance) {
        this.pluginInstance = pluginInstance;
        this.subCommands.add(new GoSubCommand("go", this.pluginInstance));
        this.subCommands.add(new SetSubCommand("set", this.pluginInstance));
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            // TODO: Make this look nicer - I hate OOP ways of solving collection issues - Make use of Map?
            if (strings.length > 0) {
                SubCommand ex = null;
                for (SubCommand sub : this.subCommands) {
                    if (sub.name().equals(strings[0])) {
                        ex = sub;
                        break;
                    }
                }

                if (ex == null) commandSender.sendMessage("Invalid sub-command for " + command.getName());
                else ex.onCommand(commandSender, Arrays.copyOfRange(strings, 1, strings.length));
            } else {
                commandSender.sendMessage("No sub-command given for " + command.getName());
            }
        } else {
            commandSender.sendMessage("Only players are allowed to use this command");
        }

        return true;
    }
}
