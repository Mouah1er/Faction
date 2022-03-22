package fr.twah2em.faction.commands.args;

import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.inventories.FactionInfoInventory;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class FactionInfo {

    public FactionInfo(Player player, OfflinePlayer targetPlayer) {
        if (targetPlayer == null) {
            player.sendMessage("§cError: This player does not exist !");
        } else {
            final Faction playerFaction = FactionJavaPlugin.getInstance().getSQL().getFactionSQL().of(targetPlayer.getUniqueId());

            if (playerFaction == null) {
                if (player.getName().equals(targetPlayer.getName())) {
                    player.sendMessage("§cError: You're not in any faction !");
                } else {
                    player.sendMessage("§cError: This player does not have any faction !");
                }
            } else {
                new FactionInfo(playerFaction.getName(), player);
            }
        }
    }

    public FactionInfo(String factionName, Player player) {
        final Faction playerFaction = FactionJavaPlugin.getInstance().getSQL().getFactionSQL().of(factionName);

        if (playerFaction == null) {
            player.sendMessage("§cError: This faction does not exist !");
        } else {
            new FactionInfoInventory(playerFaction).open(player);
        }
    }
}
