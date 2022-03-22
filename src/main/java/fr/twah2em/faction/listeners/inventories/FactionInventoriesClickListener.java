package fr.twah2em.faction.listeners.inventories;

import fr.twah2em.faction.inventories.FactionInventory;
import fr.twah2em.faction.listeners.FactionListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class FactionInventoriesClickListener implements FactionListener<InventoryClickEvent> {

    @Override
    @EventHandler
    public void onEvent(InventoryClickEvent event) {
        final InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof final FactionInventory factionInventory) {
            event.setCancelled(true);

            if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) return;

            factionInventory.onClick(event);
        }
    }
}
