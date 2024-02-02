package net.justempire.deathdroppercent;

import net.justempire.deathdroppercent.commands.DdcCommand;
import net.justempire.deathdroppercent.listeners.DeathListener;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public final class DeathDropPercent extends JavaPlugin {
    public static boolean isEnabled;

    // Percent of inventory to drop
    public static double percentToDrop;

    @Override
    public void onEnable() {
        // Creating config if it doesn't exist
        saveDefaultConfig();

        // Setting up config and some permissions
        configure();

        // Setting up death listener
        DeathListener deathListener = new DeathListener(this);
        getServer().getPluginManager().registerEvents(deathListener, this);

        // Setting up the executor for /ddc
        DdcCommand ddcCommand = new DdcCommand(this);
        this.getCommand("deathdroppercent").setExecutor(ddcCommand);

        System.out.println("[DeathDropPercent] Enabled successfully!");
    }

    private void configure() {
        // Adding overridden permissions from config
        ConfigurationSection configSection = getConfig().getConfigurationSection("customPercents");
        if (configSection != null) {
            Map<String, Object> groupsAndPercents = configSection.getValues(true);
            for (Map.Entry<String, Object> pair : groupsAndPercents.entrySet()) {
                String permissionToAdd = "deathdroppercent.custom." + pair.getKey();
                // Skip iteration if permission was already added
                if (getServer().getPluginManager().getPermission(permissionToAdd) != null) continue;
                getServer().getPluginManager().addPermission(new Permission(permissionToAdd, PermissionDefault.FALSE));
            }
        }

        isEnabled = (boolean)getConfig().get("isEnabled");
        percentToDrop = (double)getConfig().get("percentToDrop");
        saveDefaultConfig();
    }

    public void reload() {
        reloadConfig();
        configure();
    }

    @Override
    public void onDisable() {
        System.out.println("[DeathDropPercent] Shutting down!");
    }
}
