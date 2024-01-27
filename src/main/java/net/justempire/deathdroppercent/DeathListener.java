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

        event.setKeepInventory(true);
        event.getDrops().removeAll(event.getDrops());

        Player player = event.getEntity();
        Inventory inventory = player.getInventory();
        Location location = player.getLocation();
        World world = location.getWorld();
        ItemStack[] items = inventory.getStorageContents();
        double percent = DeathDropPercent.percentToDrop;

        boolean isKeepinventoryEnabled = world.getGameRuleValue("keepInventory").equals("true");
        if (isKeepinventoryEnabled) return;

        List<ItemSlot> nonEmptyItems = new ArrayList<ItemSlot>();
        for (int slotIndex = 0; slotIndex < 35; slotIndex++) {
            ItemStack item = items[slotIndex];
            if (item == null) continue;
            ItemSlot slot = new ItemSlot();
            slot.slotContent = item;
            slot.slotIndex = slotIndex;
            nonEmptyItems.add(slot);
        }

        double itemsCountToRemove = nonEmptyItems.size() * percent;
        for (int i = 0; i < itemsCountToRemove; i++) {
            Random r = new Random();
            ItemSlot itemToRemove = nonEmptyItems.get(r.nextInt(0, nonEmptyItems.size()));
            int itemToRemoveIndex = itemToRemove.slotIndex;
            world.dropItem(location, itemToRemove.slotContent);
            inventory.setItem(itemToRemoveIndex, new ItemStack(Material.AIR));
            nonEmptyItems.remove(itemToRemove);
        }
    }
}
