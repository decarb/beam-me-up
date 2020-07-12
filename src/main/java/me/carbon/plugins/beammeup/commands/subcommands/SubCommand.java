package me.carbon.plugins.beammeup.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {
    protected String permission;
    protected Boolean isConsoleAllowed;

    public String getPermission() {
        return this.permission;
    }

    public Boolean isConsoleAllowed() {
        return this.isConsoleAllowed;
    }

    public abstract void onCommand(CommandSender commandSender,
                                   Command parentCommand,
                                   String alias,
                                   String[] args);

    public abstract List<String> onTabComplete(CommandSender commandSender,
                                               Command command,
                                               String alias,
                                               String[] args);
}
