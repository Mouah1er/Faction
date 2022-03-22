package fr.twah2em.faction.inventories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class FactionInventory implements InventoryHolder {
    protected final Inventory inventory;

    public FactionInventory(int size, String name) {
        this.inventory = Bukkit.createInventory(this, size, name);
    }

    public void setItem(int slot, ItemStack itemStack) {
        this.inventory.setItem(slot, itemStack);
    }

    public void setItems(Map<Integer, ItemStack> items) {
        items.forEach(this::setItem);
    }

    public void onOpen(InventoryOpenEvent event) {
    }

    public void onClick(InventoryClickEvent event) {
    }

    public void onClose(InventoryCloseEvent event) {
    }

    public void addItem(ItemStack... itemStacks) {
        this.inventory.addItem(itemStacks);
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
