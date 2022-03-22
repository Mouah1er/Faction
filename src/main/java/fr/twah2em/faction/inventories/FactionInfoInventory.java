package fr.twah2em.faction.inventories;

import com.google.common.base.Splitter;
import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.inventories.utils.ItemBuilder;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FactionInfoInventory extends FactionInventory {
    private final Faction faction;

    public FactionInfoInventory(Faction faction) {
        super(36, "§a" + faction.getName() + " -> §6Infos");

        this.faction = faction;

        this.setItem(0, new ItemBuilder(Material.PLAYER_HEAD)
                .name("§aName: §b" + faction.getName())
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGU0MzU2NTI4MzU1OTJiN" +
                        "DZhMjQxNDhiNGNhNzQyYTRiZGY4ZjY3OGQ3ZDcwYTM4NzkyNzM4Yjg1Y2QzMyJ9fX0=")
                .build());
        final OfflinePlayer owner = Bukkit.getOfflinePlayer(faction.getOwner());
        this.setItem(1, new ItemBuilder(Material.PLAYER_HEAD)
                .name("§cOwner: §b" + (owner.isOnline() ?
                        owner.getName() + " §7(§aOnline§7)" :
                        owner.getName() + " §7(§cOffline§7)"))
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDMxNDkzZWRkNGRiNTAyM" +
                        "zhhOGQ3YzIzMTRmYjRlMjMwY2EwZGI3MTU3NjgzOWNjNmU0ZjcwODMxMTJhNDU2ZSJ9fX0=")
                .build());
        this.setItem(13, new ItemBuilder(Material.PLAYER_HEAD)
                .name("§dPower: §b" + faction.getPower())
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjBhNDA4NDRkYjMzMjYwN" +
                        "jhlYmJmMmY2MGE3NDY4ZGQxZjE5ZDY0MjExODVmZjZiMDQ1NzJjMjI4OGVhNGMwYSJ9fX0=")
                .build());
        this.setItem(21, new ItemBuilder(Material.PLAYER_HEAD)
                .name("§6Description:" + (faction.getDescription().isEmpty() ? " §cNo description §7(/f description <description>)" : ""))
                .lore(Splitter.fixedLength(40).splitToList(faction.getDescription())
                        .stream()
                        .map(s -> "§b" + s)
                        .toList())
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWQ4OGE4NjQ0NWQ0MTE0Y" +
                        "jE1MTcxYmI5ZWY4Mzc0NjA2MGNhZmZmMmNiMmFhNzUxMTU5NzQwY2ZmNDhkZjg5ZSJ9fX0=")
                .build());
        this.setItem(22, new ItemBuilder(Material.PLAYER_HEAD)
                .name("§7Creation date: §b" + faction.getCreationDate().substring(0, 16))
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTQyYzg2ODczYTE1NWI4M" +
                        "mU2NmU4MjdmOWFjYmFmOWM2MDBiYjAxZmVmNzBkODc1ZDNmOWI0OWQwMjc5Y2I0NyJ9fX0=")
                .build());
        this.setItem(23, new ItemBuilder(Material.PLAYER_HEAD)
                .name("§9Players: §7(Click)")
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDlmODU4MzFlNWU0N2Q1Z" +
                        "TBhODFjMWY5MjAwMzU0OGE5ZjBkZmIwYTA3ZTliYWM5MzdjMTlhMTQ5NDI0YzYwZiJ9fX0=")
                .build());
        final List<String> claims = new ArrayList<>(faction.getClaims()
                .stream()
                .map(claim -> "§7X: " + claim.getX() + " | Z: " + claim.getZ())
                .toList());
        this.setItem(31, new ItemBuilder(Material.PLAYER_HEAD)
                .name("§eClaims:" + (faction.getClaims().isEmpty() ? " §cNo chunks §7(/f claim)" : ""))
                .lore(claims)
                .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjg3MDNmNjY3MzY1MGQzNj" +
                        "E2MTI5MTAyN2NiMGNmOGZiYjdhMzM5ZDY2ODgzMTc2NzQzZjQwMjQ3MzM1NDg5MyJ9fX0")
                .build());
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final ItemStack currentItem = event.getCurrentItem();

        final TextComponent textComponent = (TextComponent) currentItem.getItemMeta().displayName().children().get(0);

        if (textComponent.content().contains("Players")){
            new FactionInfoPlayersInventory(faction).open(player);
        }
    }
}
