package fr.twah2em.faction.inventories;

import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.database.FactionSQL;
import fr.twah2em.faction.inventories.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

public class FactionInfoPlayersInventory extends FactionInventory {

    public FactionInfoPlayersInventory(Faction faction) {
        super(54, "§a" + faction.getName() + " -> §9Players");

        final FactionSQL factionSQL = FactionJavaPlugin.getInstance().getSQL().getFactionSQL();

        faction.getPlayersIn().forEach(uuid -> {
            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            addItem(new ItemBuilder(Material.PLAYER_HEAD)
                    .head(offlinePlayer)
                    .name("§b" + offlinePlayer.getName() + (offlinePlayer.isOnline() ?
                            " §7(§aOnline§7)" :
                            " §7(§cOffline§7)"))
                    .lore("§2Rôle: " + factionSQL.getPlayersInFactionSQL().getRole(faction, offlinePlayer.getUniqueId()))
                    .build());
        });
    }
}
