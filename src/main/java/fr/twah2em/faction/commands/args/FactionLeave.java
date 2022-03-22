package fr.twah2em.faction.commands.args;

import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.database.FactionSQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class FactionLeave {

    public void send(Player player) {
        final FactionSQL factionSQL = FactionJavaPlugin.getInstance().getSQL().getFactionSQL();

        if (!factionSQL.hasFaction(player.getUniqueId())) {
            player.sendMessage("§cError: You're not in any faction !");
        } else {
            final Faction playerFaction = factionSQL.of(player.getUniqueId());

            if (playerFaction.getOwner().equals(player.getUniqueId())) {
                player.sendMessage("§cError: You're the owner of your faction, please use §6/f delete §cto delete your faction, or §6" +
                        "/f lead <player> §cto set the lead to a member of your faction !");
            } else {
                factionSQL.getPlayersInFactionSQL().makePlayerLeave(playerFaction, player.getUniqueId());
                playerFaction.getPlayersIn().stream().map(Bukkit::getPlayer)
                        .filter(Objects::nonNull)
                        .forEach(player1 -> player1.sendMessage("§b" + player.getName() + "§c has left your faction !"));
                player.sendMessage("§aYou have left the faction §b" + playerFaction.getName() + "§a !");
            }
        }
    }
}
