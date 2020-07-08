package me.carbon.plugins.beammeup.commands.subcommands;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    public abstract String name();
    public abstract void onCommand(CommandSender commandSender, String[] strings);
}
