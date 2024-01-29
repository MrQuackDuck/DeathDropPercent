package net.justempire.deathdroppercent;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeathListener implements Listener {
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

        // If keepInventory in the world is enabled, then stop further execution
        boolean keepInventoryEnabled = world.getGameRuleValue("keepInventory").equals("true");
        if (keepInventoryEnabled) return;

        // Getting inventory items that are not air
        List<ItemSlot> nonEmptyItems = new ArrayList<ItemSlot>();
        for (int slotIndex = 0; slotIndex < 40; slotIndex++) {
            ItemStack item = items[slotIndex];
            if (item == null) continue;
            ItemSlot slot = new ItemSlot();
            slot.slotContent = item;
            slot.slotIndex = slotIndex;
            nonEmptyItems.add(slot);
        }

        // Getting the items count to remove based on the percentage provided from the config
        double itemsCountToRemove = nonEmptyItems.size() * percentage;
        for (int i = 0; i < itemsCountToRemove; i++) {
            Random r = new Random();
            ItemSlot itemToRemove = nonEmptyItems.get(r.nextInt(nonEmptyItems.size()));
            int itemToRemoveIndex = itemToRemove.slotIndex;
            world.dropItem(location, itemToRemove.slotContent);
            inventory.setItem(itemToRemoveIndex, new ItemStack(Material.AIR));
            nonEmptyItems.remove(itemToRemove);
        }
    }
}
