package fr.twah2em.faction.commands.args;

import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.database.FactionSQL;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Objects;

public class FactionLead {

    public void send(Player player, String targetPlayerName) {
        final OfflinePlayer targetPlayer = Bukkit.getOfflinePlayerIfCached(targetPlayerName);
        final FactionSQL factionSQL = FactionJavaPlugin.getInstance().getSQL().getFactionSQL();

        if (targetPlayer == null) {
            player.sendMessage("§cError: This player does not exist !");
        } else {
            final Faction playerFaction = factionSQL.of(player.getUniqueId());

            if (playerFaction == null) {
                player.sendMessage("§cError: You're not in any faction !");
            } else {
                if (!playerFaction.getOwner().equals(player.getUniqueId())) {
                    player.sendMessage("§cError: You're not the owner of your faction !");
                } else {
                    if (player.getUniqueId().equals(targetPlayer.getUniqueId())) {
                        player.sendMessage("§cError: You are already the owner of your faction !");
                    } else {
                        if (!playerFaction.getPlayersIn().contains(targetPlayer.getUniqueId())) {
                            player.sendMessage("§cError: This player is not in you're faction !");
                        } else {
                            factionSQL.setOwner(playerFaction, targetPlayer.getUniqueId());
                            playerFaction.getPlayersIn()
                                    .stream()
                                    .map(Bukkit::getPlayer)
                                    .filter(Objects::nonNull)
                                    .forEach(player1 -> player1.sendMessage("§6The player §b" + targetPlayerName + " §6has been set to the owner " +
                                            "of your faction !"));
                        }
                    }
                }
            }
        }
    }
}
