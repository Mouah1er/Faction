package fr.twah2em.faction.commands.args;

import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.database.FactionSQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FactionUninvite {

    public void send(Player player, String targetPlayerName) {
        final Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
        final FactionSQL factionSQL = FactionJavaPlugin.getInstance().getSQL().getFactionSQL();

        if (targetPlayer == null) {
            player.sendMessage("§cError: This player is not connected !");
        } else {
            final Faction playerFaction = factionSQL.of(player.getUniqueId());

            if (playerFaction == null) {
                player.sendMessage("§cError: You're not in any faction !");
            } else {
                if (player.getName().equals(targetPlayerName)) {
                    player.sendMessage("§cError: You can't uninvite yourself !");
                } else {
                    if (!playerFaction.getInvitedPlayers().contains(targetPlayer.getUniqueId())) {
                        player.sendMessage("§cError: This player is not invited in your faction !");
                    } else {
                        if (!playerFaction.getOwner().equals(player.getUniqueId())) {
                            player.sendMessage("§cError: You are not the owner of your faction !");
                        } else {
                            factionSQL.getInvitedPlayersInFactionSQL().removeInvitation(playerFaction, targetPlayer.getUniqueId());
                            targetPlayer.sendMessage("§cYou have been disinvited from the faction §b" + playerFaction.getName() + " §c!");
                            player.sendMessage("§aYou have successfully uninvited §b" + targetPlayer.getName() + " §a!");
                        }
                    }
                }
            }
        }
    }
}
