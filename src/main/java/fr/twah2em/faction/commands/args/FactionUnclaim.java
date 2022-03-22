package fr.twah2em.faction.commands.args;

import fr.twah2em.faction.engine.Claim;
import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.database.FactionSQL;
import org.bukkit.entity.Player;

public class FactionUnclaim {

    public void send(Player player) {
        final FactionSQL factionSQL = FactionJavaPlugin.getInstance().getSQL().getFactionSQL();
        final Faction playerFaction = factionSQL.of(player.getUniqueId());

        if (playerFaction == null) {
            player.sendMessage("§cError: You're not in any faction !");
        } else {
            final Claim claim = factionSQL.getClaimSQL().of(player.getChunk());

            if (claim == null) {
                player.sendMessage("§cError: This chunk is not a claim of anyone !");
            } else {
                if (!factionSQL.getClaimSQL().isClaimedByFaction(playerFaction, player.getChunk())) {
                    player.sendMessage("§cError: This chunk is claimed by §6" + claim.getOwnerFactionName() + "§c !");
                } else {
                    factionSQL.getClaimSQL().unClaim(playerFaction, player.getChunk());
                    player.sendMessage("§aYou have unclaimed this chunk !");

                }
            }
        }
    }
}
