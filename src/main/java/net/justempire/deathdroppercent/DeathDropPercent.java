package net.justempire.deathdroppercent;

import org.bukkit.plugin.java.JavaPlugin;

public final class DeathDropPercent extends JavaPlugin {
    public static boolean isEnabled;

    // Percent of inventory to drop
    public static double percentToDrop;

    @Override
    public void onEnable() {
        // Creating config
        saveDefaultConfig();

        if (!getConfig().contains("isEnabled")) {
            getConfig().set("isEnabled", true);
            isEnabled = true;
        } else {
            isEnabled = (boolean)getConfig().get("isEnabled");
        }

        if (!getConfig().contains("percentToDrop")) {
            getConfig().set("percentToDrop", 0.5);
            percentToDrop = 0.5;
        }
        else {
            percentToDrop = (double)getConfig().get("percentToDrop");
        }

        getConfig().set("isEnabled", isEnabled);
        getConfig().set("percentToDrop", percentToDrop);
        saveConfig();

        DeathListener deathListener = new DeathListener();
        getServer().getPluginManager().registerEvents(deathListener, this);

        DdcCommand ddcCommand = new DdcCommand(this);
        this.getCommand("deathdroppercent").setExecutor(ddcCommand);
    }

    @Override
    public void onDisable() {
        System.out.println("Shutting down!");
    }
}
