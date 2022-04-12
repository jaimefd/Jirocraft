package me.jiroscopio.jirocraftplugin.commands;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ZonesCommand implements CommandExecutor {

    private final JirocraftPlugin plugin;

    public ZonesCommand(JirocraftPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String s, String @NonNull [] strings) {
        this.plugin.zoneGenerator.resetZones();
        this.plugin.zoneGenerator.printZones();
        return true;
    }
}
