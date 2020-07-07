package me.carbon.plugins.beammeup.commands;

import me.carbon.plugins.beammeup.BeamMeUp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class BeamCommandManager implements CommandExecutor {
    private BeamMeUp pluginInstance;
    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    public BeamCommandManager(BeamMeUp pluginInstance) {
        this.pluginInstance = pluginInstance;
        this.subCommands.add(new GoSubCommand("go"));
        this.subCommands.add(new SetSubCommand("set"));
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage("Calling beam command");
        return true;
    }
}
