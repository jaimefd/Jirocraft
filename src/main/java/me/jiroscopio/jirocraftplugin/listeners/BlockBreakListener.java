package me.jiroscopio.jirocraftplugin.listeners;

import me.jiroscopio.jirocraftplugin.JirocraftPlugin;
import me.jiroscopio.jirocraftplugin.enums.Rarity;
import me.jiroscopio.jirocraftplugin.enums.ItemType;
import me.jiroscopio.jirocraftplugin.helpers.ItemHelper;
import me.jiroscopio.jirocraftplugin.records.BlockRecord;
import me.jiroscopio.jirocraftplugin.records.FacingRecord;
import me.jiroscopio.jirocraftplugin.records.FamilyRecord;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
import me.jiroscopio.jirocraftplugin.records.drops.DropEntry;
import me.jiroscopio.jirocraftplugin.records.drops.DropPool;
import me.jiroscopio.jirocraftplugin.records.drops.DropsRecord;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class BlockBreakListener implements Listener {

    private final JirocraftPlugin plugin;

    public BlockBreakListener(JirocraftPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Random rand = new Random();
        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        Block brokenBlock = e.getBlock();
        String blockName = brokenBlock.getType().toString();
        BlockRecord blockRecord = this.plugin.blockRecords.get(blockName);
        if (blockRecord == null) {
            FacingRecord facingRecord = this.plugin.facingRecords.get(brokenBlock.getType());
            if (facingRecord != null) {
                BlockData blockData = brokenBlock.getBlockData();
                if (blockData instanceof Directional) {
                    BlockFace face = ((Directional) blockData).getFacing();
                    String real_block = facingRecord.faces().get(face);
                    if (real_block != null) {
                        blockName = real_block;
                        blockRecord = this.plugin.blockRecords.get(blockName);
                    }
                }
            }
        }

        // Stop player from breaking custom blocks with a lower tier tool
        if (blockRecord != null) {
            if (blockRecord.tool() != ItemType.ANY) {
                ItemStack main_hand_item = p.getInventory().getItemInMainHand();
                String item_type = ItemHelper.getItemType(this.plugin, main_hand_item);
                ItemRecord tool_record =  this.plugin.itemRecords.get(item_type);
                if (tool_record != null) {
                    if (tool_record.type() == blockRecord.tool()) {
                        if (tool_record.tool_power() < blockRecord.tool_tier()) {
                            p.sendMessage(ChatColor.RED + "You need a better tool to break this block!");
                            e.setCancelled(true);
                            return;
                        }
                    }
                }
            }
        }

        ArrayList<DropsRecord> dropsRecord = new ArrayList<>();
        DropsRecord record;
        if (blockRecord == null) {
            record = this.plugin.dropRecords.get(blockName.toLowerCase());
            if (record != null) dropsRecord.add(record);
        } else if (blockRecord.drops().isEmpty()) {
            record = this.plugin.dropRecords.get(blockName.toLowerCase());
            if (record != null) dropsRecord.add(record);
        } else {
            for (String drop : blockRecord.drops()) {
                record = this.plugin.dropRecords.get(drop);
                if (record != null) dropsRecord.add(record);
            }
        }

        if (!dropsRecord.isEmpty()) {
            e.setDropItems(false);
            ArrayList<DropEntry> entries_to_drop = new ArrayList<>();

            for (DropsRecord dropSet : dropsRecord) {
                for (DropPool pool : dropSet.pools()) {
                    if (blockRecord != null && pool.tool() && blockRecord.tool() != ItemType.ANY) {
                        ItemStack main_hand_item = p.getInventory().getItemInMainHand();
                        String item_type = ItemHelper.getItemType(this.plugin, main_hand_item);
                        ItemRecord tool_record =  this.plugin.itemRecords.get(item_type);
                        if (tool_record == null) continue;
                        if (tool_record.type() != blockRecord.tool()) continue;
                        if (tool_record.tool_power() < pool.tool_power_min()) continue;
                        if (tool_record.tool_power() > pool.tool_power_max() && pool.tool_power_max() > 0) continue;
                    }
                    if (rand.nextDouble() < pool.chance()) {
                        int attempts = 1;
                        if (pool.rolls() != null) attempts = pool.rolls().generateNumber(rand);
                        for (int i = 0; i < attempts; i++) {
                            if (pool.single_entry() != null) {
                                entries_to_drop.add(pool.single_entry());
                            } else {
                                DropEntry chosen_drop = null;
                                int choice = rand.nextInt(pool.total_weight());
                                for (DropEntry entry : pool.entries()) {
                                    if (choice < entry.weight()) {
                                        chosen_drop = entry;
                                        break;
                                    } else choice -= entry.weight();
                                }
                                if (chosen_drop != null) entries_to_drop.add(chosen_drop);
                            }
                        }
                    }
                }
            }

            for (DropEntry entry : entries_to_drop) {
                if (!entry.type().equals("item")) continue;
                ItemRecord item = this.plugin.itemRecords.get(entry.drop());
                if (item == null) continue;

                // Announcing can be pre-selected by the entry, or always happen after 2 consecutive tier-ups
                int announce_score = entry.announce();

                if (entry.tiered() && item.family() != null) {
                    FamilyRecord family = this.plugin.familyRecords.get(item.family());
                    if (family != null) {
                        String newItem = null;
                        int increases = 0;
                        int tier = family.tiers().indexOf(item.id());
                        if (tier > -1) {
                            while (rand.nextInt(family.upgrade()) == 0 && tier < (family.tiers().size() - 1)) {
                                tier++;
                                increases++;
                                newItem = family.tiers().get(tier);
                            }
                            if (newItem != null) {
                                if (this.plugin.itemRecords.get(newItem) != null) {
                                    item = this.plugin.itemRecords.get(newItem);
                                    announce_score += increases;
                                }
                            }
                        }
                    }
                }

                ItemStack stack = item.getItemStack(null, this.plugin);
                if (stack == null) continue;
                int amount = 1;
                if (entry.amount() != null) amount = entry.amount().generateNumber(rand);
                stack.setAmount(amount);

                Item dropped_item = brokenBlock.getWorld().dropItemNaturally(brokenBlock.getLocation(), stack);
                brokenBlock.setType(Material.END_STONE);
                dropped_item.setGlowing(true);

                // Announce to player only if score is 2. If more, broadcast to the server (and for the future, when multiple servers, score of 4 for all)
                if (stack.getItemMeta() == null || !stack.getItemMeta().hasDisplayName()) return;
                String found_message_one = " found " + stack.getItemMeta().getDisplayName() + ChatColor.YELLOW + "!";
                String found_message_several = " found " + amount + " " + stack.getItemMeta().getDisplayName() + ChatColor.YELLOW + "!";
                if (announce_score >= 2) {
                    if (amount == 1) p.sendMessage(ChatColor.YELLOW + "You" + found_message_one);
                    else p.sendMessage(ChatColor.YELLOW + "You" + found_message_several);
                }

                if (announce_score >= 3) {
                    if (amount == 1) Bukkit.broadcastMessage(ChatColor.AQUA + p.getDisplayName() + ChatColor.YELLOW + found_message_one);
                    else Bukkit.broadcastMessage(ChatColor.AQUA + p.getDisplayName() + ChatColor.YELLOW + found_message_several);
                }

                String team_tag = Rarity.values()[item.rarity()].toString().toLowerCase();
                Team team = board.getTeam(team_tag);
                if (team != null) team.addEntry(dropped_item.getUniqueId().toString());
            }
        }

    }

}
