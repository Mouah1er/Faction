package fr.twah2em.faction.listeners.inventories;

import fr.twah2em.faction.inventories.FactionInventory;
import fr.twah2em.faction.listeners.FactionListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

public class FactionInventoriesOpenListener implements FactionListener<InventoryOpenEvent> {

    @Override
    @EventHandler
    public void onEvent(InventoryOpenEvent event) {
        final InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof final FactionInventory factionInventory) {
            factionInventory.onOpen(event);
        }
    }
}
