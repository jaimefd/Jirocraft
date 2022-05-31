package me.jiroscopio.jirocraftplugin.commands;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.helpers.ItemHelper;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public class GetItemCommand implements CommandExecutor {

    private final JirocraftPlugin plugin;

    public GetItemCommand(JirocraftPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {
        if (args.length > 0 && sender instanceof Player){
            Player p = (Player) sender;
            if (p.isOp()) {
                ItemStack item = ItemHelper.getItemByName(this.plugin, args[0]);
                if (item == null) return false;
                if (args.length > 1) {
                    if (args[1] == null) return false;
                    item.setAmount(Integer.parseInt(args[1]));
                }
                p.getInventory().addItem(item);
                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5F, 1F);
                return true;
            } else {
                p.sendMessage("§cYou don't have permission to use this command!");
            }
        }
        return false;
    }
}
