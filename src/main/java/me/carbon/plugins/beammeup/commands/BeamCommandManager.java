package me.carbon.plugins.beammeup.commands;

import me.carbon.plugins.beammeup.BeamMeUp;
import me.carbon.plugins.beammeup.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;

import java.util.*;
import java.util.stream.Collectors;

// TODO: Abstract out command manager
// TODO: Abstract out error messages
public class BeamCommandManager implements TabExecutor {
    private final BeamMeUp pluginInstance;
    private final Map<String, SubCommand> subCommands;
    private final String name;

    public BeamCommandManager(String name, BeamMeUp pluginInstance) {
        this.pluginInstance = pluginInstance;
        this.name = name;

        this.subCommands = new HashMap<>();
        this.subCommands.put("go", new GoSubCommand(this.pluginInstance));
        this.subCommands.put("set", new SetSubCommand(this.pluginInstance));
        this.subCommands.put("list", new ListSubCommand(this.pluginInstance));
        this.subCommands.put("remove", new RemoveSubCommand(this.pluginInstance));
    }

    private void onBaseCommand(CommandSender commandSender, Command command, String s) {
        commandSender.sendMessage(this.pluginInstance.getCommand(name).getUsage());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length > 0) {
            if (this.subCommands.containsKey(strings[0])) {
                SubCommand sub = this.subCommands.get(strings[0]);
                String[] args = Arrays.copyOfRange(strings, 1, strings.length);
                sub.onCommand(commandSender, command, s, args);
            } else commandSender.sendMessage("Invalid sub-command " + strings[0]);
        } else this.onBaseCommand(commandSender, command, s);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return this.subCommands.entrySet().stream()
                    .filter(e -> {
                        if (sender instanceof ConsoleCommandSender) return e.getValue().isConsoleAllowed();
                        else return sender.hasPermission(e.getValue().getPermission())
                                && e.getKey().startsWith(args[0]);
                    })
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        } else if (this.subCommands.containsKey(args[0])) {
            String[] argStrings = Arrays.copyOfRange(args, 1, args.length);
            return this.subCommands.get(args[0]).onTabComplete(sender, command, alias, argStrings);
        } else return new ArrayList<>();
    }
}
