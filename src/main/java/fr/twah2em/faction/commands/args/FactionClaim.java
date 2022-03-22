package fr.twah2em.faction.commands.args;

import fr.twah2em.faction.engine.Claim;
import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.database.FactionSQL;
import org.bukkit.entity.Player;

public class FactionClaim {

    public void send(Player player) {
        final FactionSQL factionSQL = FactionJavaPlugin.getInstance().getSQL().getFactionSQL();
        final Faction playerFaction = factionSQL.of(player.getUniqueId());

        if (playerFaction == null) {
            player.sendMessage("§cError: You're not in any faction !");
        } else {
            if (factionSQL.getClaimSQL().isClaimedByFaction(playerFaction, player.getChunk())) {
                player.sendMessage("§cError: This chunk is already claimed by §6your §cfaction !");
            } else {
                final Claim claim = factionSQL.getClaimSQL().of(player.getChunk());

                if (claim != null) {
                    player.sendMessage("§cError: This chunk is already claimed by the §6" + claim.getOwnerFactionName() + " §cfaction !");
                } else {
                    factionSQL.getClaimSQL().claim(playerFaction, player.getChunk());
                    player.sendMessage("§aYou have claimed this chunk !");
                }
            }
        }
    }
}
