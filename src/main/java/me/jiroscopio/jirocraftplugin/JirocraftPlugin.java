package me.jiroscopio.jirocraftplugin;

import me.jiroscopio.jirocraftplugin.commands.ZonesCommand;
import me.jiroscopio.jirocraftplugin.files.*;
import me.jiroscopio.jirocraftplugin.helpers.ZoneGenerator;
import me.jiroscopio.jirocraftplugin.listeners.JoinListener;
import me.jiroscopio.jirocraftplugin.listeners.BlockBreakListener;
import me.jiroscopio.jirocraftplugin.listeners.PickupItemListener;
import me.jiroscopio.jirocraftplugin.populators.*;
import me.jiroscopio.jirocraftplugin.records.BlockRecord;
import me.jiroscopio.jirocraftplugin.records.FacingRecord;
import me.jiroscopio.jirocraftplugin.records.FamilyRecord;
import me.jiroscopio.jirocraftplugin.records.ItemRecord;
import me.jiroscopio.jirocraftplugin.records.drops.DropsRecord;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public final class JirocraftPlugin extends JavaPlugin implements Listener {

    public ItemManager itemManager;
    public BlockManager blockManager;
    public FacingManager facingManager;
    public FamilyManager familyManager;
    public DropsManager dropsManager;
    public ZoneGenerator zoneGenerator;

    public HashMap<String, ItemRecord> itemRecords = new HashMap<>();
    public HashMap<String, BlockRecord> blockRecords = new HashMap<>();
    public HashMap<Material, FacingRecord> facingRecords = new HashMap<>();
    public HashMap<String, DropsRecord> dropRecords = new HashMap<>();
    public HashMap<String, FamilyRecord> familyRecords = new HashMap<>();

    @Override
    public void onEnable() {
        System.out.println("Jirocraft plugin enabled");

        this.itemManager = new ItemManager(this, "items.yml");
        this.itemManager.setupManager();
        this.blockManager = new BlockManager(this, "blocks.yml");
        this.blockManager.setupManager();
        this.facingManager = new FacingManager(this, "faces.yml");
        this.facingManager.setupManager();
        this.familyManager = new FamilyManager(this, "families.yml");
        this.familyManager.setupManager();
        this.dropsManager = new DropsManager(this, "drops.yml");
        this.dropsManager.setupManager();

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        //getServer().getPluginManager().registerEvents(new ChunkPopulateListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new PickupItemListener(this), this);

        getCommand("zones").setExecutor(new ZonesCommand(this));

        zoneGenerator = new ZoneGenerator(32,7);
    }

    @Override
    public void onDisable() {
        System.out.println("Jirocraft plugin disabled");
    }

    @EventHandler
    public void onWorldInit(WorldInitEvent e) {
        if (e.getWorld().getName().equalsIgnoreCase("world")) {
            System.out.println("Add custom populators");
            //getServer().getWorld("world").getPopulators().add((BlockPopulator)new TestPopulator());
            //getServer().getWorld("world").getPopulators().add((BlockPopulator)new TestPopulator2());
            getServer().getWorld("world").getPopulators().add(new TinOrePopulator());
            getServer().getWorld("world").getPopulators().add(new ZincOrePopulator());
            getServer().getWorld("world").getPopulators().add(new SilverOrePopulator());
            getServer().getWorld("world").getPopulators().add(new ManganeseOrePopulator());
            getServer().getWorld("world").getPopulators().add(new UraniumOrePopulator());
            getServer().getWorld("world").getPopulators().add(new IridiumOrePopulator());
            getServer().getWorld("world").getPopulators().add(new PlatinumOrePopulator());
            getServer().getWorld("world").getPopulators().add(new AluminiumOrePopulator());
            getServer().getWorld("world").getPopulators().add(new LeadOrePopulator());
            getServer().getWorld("world").getPopulators().add(new TitaniumOrePopulator());
            getServer().getWorld("world").getPopulators().add(new CobaltOrePopulator());
            getServer().getWorld("world").getPopulators().add(new TungstenOrePopulator());
            getServer().getWorld("world").getPopulators().add(new EssenceOrePopulator());
            getServer().getWorld("world").getPopulators().add(new CinnabarOrePopulator());
            getServer().getWorld("world").getPopulators().add(new SapphireOrePopulator());
            getServer().getWorld("world").getPopulators().add(new TopazOrePopulator());

            Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
            if (board.getTeam("basic") == null) board.registerNewTeam("basic").setColor(ChatColor.WHITE);
            if (board.getTeam("common") == null) board.registerNewTeam("common").setColor(ChatColor.GREEN);
            if (board.getTeam("uncommon") == null) board.registerNewTeam("uncommon").setColor(ChatColor.DARK_GREEN);
            if (board.getTeam("rare") == null) board.registerNewTeam("rare").setColor(ChatColor.BLUE);
            if (board.getTeam("very_rare") == null) board.registerNewTeam("very_rare").setColor(ChatColor.DARK_BLUE);
            if (board.getTeam("epic") == null) board.registerNewTeam("epic").setColor(ChatColor.DARK_PURPLE);
            if (board.getTeam("legendary") == null) board.registerNewTeam("legendary").setColor(ChatColor.GOLD);
            if (board.getTeam("mythic") == null) board.registerNewTeam("mythic").setColor(ChatColor.DARK_RED);
        } else if (e.getWorld().getName().equalsIgnoreCase("world_nether")) {
            getServer().getWorld("world_nether").getPopulators().add(new RubyOrePopulator());
            getServer().getWorld("world_nether").getPopulators().add(new SulfurOrePopulator());
            getServer().getWorld("world_nether").getPopulators().add(new SoulOrePopulator());
            getServer().getWorld("world_nether").getPopulators().add(new MithrilOrePopulator());
            getServer().getWorld("world_nether").getPopulators().add(new ChromiumOrePopulator());
        } else if (e.getWorld().getName().equalsIgnoreCase("world_the_end")) {
            getServer().getWorld("world_the_end").getPopulators().add(new IoliteOrePopulator());
            getServer().getWorld("world_the_end").getPopulators().add(new NickelOrePopulator());
            getServer().getWorld("world_the_end").getPopulators().add(new ChorusOrePopulator());
            getServer().getWorld("world_the_end").getPopulators().add(new VoidOrePopulator());
        }
    }


}
