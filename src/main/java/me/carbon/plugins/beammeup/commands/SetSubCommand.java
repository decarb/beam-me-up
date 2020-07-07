package me.carbon.plugins.beammeup.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SetSubCommand extends SubCommand {
    private String name;

    public SetSubCommand(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage("Calling set sub-command");
    }
}
