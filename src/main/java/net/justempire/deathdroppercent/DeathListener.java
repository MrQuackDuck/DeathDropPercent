package net.justempire.deathdroppercent;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.BiConsumer;

public class DeathListener implements Listener {
    private JavaPlugin plugin;

    public DeathListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (!DeathDropPercent.isEnabled) return;

        // Overriding default behaviour of death
        event.setKeepInventory(true);
        event.getDrops().removeAll(event.getDrops());

        // Setting up variables to work with
        Player player = event.getEntity();
        Inventory inventory = player.getInventory();
        Location location = player.getLocation();
        World world = location.getWorld();
        ItemStack[] items = inventory.getContents();
        double percentage = DeathDropPercent.percentToDrop;

        // Getting custom percents from config
        ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("customPercents");
        // Creating empty map
        Map<String, Object> customPercents = new HashMap<>();
        // Setting map values if configSection isn't null
        if (configSection != null) customPercents = configSection.getValues(true);
        for (Map.Entry<String, Object> pair : customPercents.entrySet()) {
            if (!player.hasPermission("deathdroppercent.custom." + pair.getKey())) continue;

            try { percentage = (double)pair.getValue(); }
            catch (Exception e) { System.out.println(e.getMessage()); }
        }

        // If keepInventory in the world is enabled, then stop further execution
        boolean keepInventoryEnabled = world.getGameRuleValue("keepInventory").equals("true");
        if (keepInventoryEnabled) return;

        // Getting inventory items that are not air
        List<ItemSlot> nonEmptyItems = new ArrayList<>();
        for (int slotIndex = 0; slotIndex < 41; slotIndex++) {
            ItemStack item = items[slotIndex];
            if (item == null) continue;
            ItemSlot slot = new ItemSlot();
            slot.slotContent = item;
            slot.slotIndex = slotIndex;
            nonEmptyItems.add(slot);
        }

        // Removing items with curse of vanishing (without dropping them)
        for (int i = 0; i < nonEmptyItems.size(); i++) {
            ItemSlot itemToRemove = nonEmptyItems.get(i);
            int itemToRemoveIndex = itemToRemove.slotIndex;

            if (!itemToRemove.slotContent.containsEnchantment(Enchantment.VANISHING_CURSE)) continue;

            inventory.setItem(itemToRemoveIndex, new ItemStack(Material.AIR));
            nonEmptyItems.remove(itemToRemove);
            i--;
        }

        // Getting the items count to remove based on the percentage provided from the config
        double itemsCountToRemove = nonEmptyItems.size() * percentage;
        for (int i = 0; i < itemsCountToRemove; i++) {
            Random r = new Random();
            ItemSlot itemToRemove = nonEmptyItems.get(r.nextInt(nonEmptyItems.size()));
            int itemToRemoveIndex = itemToRemove.slotIndex;

            if (player.hasPermission("deathdroppercent.drop")) {
                world.dropItem(location, itemToRemove.slotContent);
            }

            inventory.setItem(itemToRemoveIndex, new ItemStack(Material.AIR));
            nonEmptyItems.remove(itemToRemove);
        }
    }
}
