package me.carbon.plugins.beammeup.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class GoSubCommand extends SubCommand {
    private final String name;

    public GoSubCommand(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] strings) {
        commandSender.sendMessage("Calling go sub-command");
    }
}
