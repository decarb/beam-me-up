package me.carbon.plugins.beammeup.commands;

import me.carbon.plugins.beammeup.BeamMeUp;
import me.carbon.plugins.beammeup.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

// TODO: Use a listener to remove the stupid extra command?
public class BeamCommandManager implements TabExecutor {
    private final BeamMeUp pluginInstance;
    private final Map<String, SubCommand> subCommands;

    // TODO: Add command for "help"
    // TODO: Remove names from subcommands interface
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
                    sub.onCommand(commandSender, command, s, args);
                } else commandSender.sendMessage("Invalid sub-command " + strings[0]);
            } else commandSender.sendMessage("No sub-command given for " + command.getName());
        } else commandSender.sendMessage("Only players are allowed to use this command");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (args.length) {
            case 1:
                return this.subCommands.entrySet().stream()
                        .filter(e -> sender.hasPermission(e.getValue().getPermission()) && e.getKey().startsWith(args[0]))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
            case 2:
                if (this.subCommands.containsKey(args[0])) {
                    String[] argStrings = Arrays.copyOfRange(args, 1, args.length);
                    return this.subCommands.get(args[0]).onTabComplete(sender, command, alias, argStrings);
                } else return Collections.emptyList();
            default:
                return Collections.emptyList();
        }
    }
}
