package me.carbon.plugins.beammeup.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {
    public abstract String getPermission();

    public abstract void onCommand(CommandSender commandSender,
                                   Command parentCommand,
                                   String alias,
                                   String[] args);

    public abstract List<String> onTabComplete(CommandSender commandSender,
                                               Command command,
                                               String alias,
                                               String[] args);
}
