package me.carbon.plugins.beammeup.commands;

import me.carbon.plugins.beammeup.BeamMeUp;
import me.carbon.plugins.beammeup.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Implement TagCompleter (not too important)
public class BeamCommandManager implements CommandExecutor {
    private final BeamMeUp pluginInstance;
    private final Map<String, SubCommand> subCommands;

    // TODO: Add command for "help"
    public BeamCommandManager(BeamMeUp pluginInstance) {
        this.pluginInstance = pluginInstance;
        this.subCommands = new HashMap<>();
        this.subCommands.put("go", new GoSubCommand("go", this.pluginInstance));
        this.subCommands.put("set", new SetSubCommand("set", this.pluginInstance));
        this.subCommands.put("list", new ListSubCommand("list", this.pluginInstance));
        this.subCommands.put("remove", new RemoveSubCommand("remove", this.pluginInstance));
    }

    // TODO: Allow for certain sub-commands to be run from the console
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (strings.length > 0) {
                if (this.subCommands.containsKey(strings[0])) {
                    SubCommand sub = this.subCommands.get(strings[0]);
                    String[] args = Arrays.copyOfRange(strings, 1, strings.length);
                    sub.onCommand(commandSender, args);
                } else commandSender.sendMessage("Invalid sub-command " + strings[0]);
            } else commandSender.sendMessage("No sub-command given for " + command.getName());
        } else commandSender.sendMessage("Only players are allowed to use this command");

        return true;
    }
}
