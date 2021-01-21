package me.carbon.plugins.beammeup.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {
    private final String permission;
    private final Boolean consoleAllowed;

    public SubCommand(String permission, Boolean consoleAllowed) {
        this.permission = permission;
        this.consoleAllowed = consoleAllowed;
    }

    public String getPermission() {
        return this.permission;
    }

    public Boolean isConsoleAllowed() {
        return this.consoleAllowed;
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
