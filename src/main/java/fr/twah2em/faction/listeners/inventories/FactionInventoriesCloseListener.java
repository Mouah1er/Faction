package fr.twah2em.faction.listeners.inventories;

import fr.twah2em.faction.inventories.FactionInventory;
import fr.twah2em.faction.listeners.FactionListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public class FactionInventoriesCloseListener implements FactionListener<InventoryCloseEvent> {

    @Override
    @EventHandler
    public void onEvent(InventoryCloseEvent event) {
        final InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof final FactionInventory factionInventory) {
            factionInventory.onClose(event);
        }
    }
}
