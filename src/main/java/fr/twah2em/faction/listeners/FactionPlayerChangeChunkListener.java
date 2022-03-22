package fr.twah2em.faction.listeners;

import fr.twah2em.faction.engine.Claim;
import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.database.FactionSQL;
import fr.twah2em.faction.events.FactionPlayerChangeChunkEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class FactionPlayerChangeChunkListener implements FactionListener<FactionPlayerChangeChunkEvent> {

    @Override
    @EventHandler
    public void onEvent(FactionPlayerChangeChunkEvent event) {
        final Player player = event.getPlayer();
        final Chunk to = event.getTo();
        final Chunk from = event.getFrom();
        final FactionSQL factionSQL = FactionJavaPlugin.getInstance().getSQL().getFactionSQL();

        final Claim claimTo = factionSQL.getClaimSQL().of(to);
        final Claim claimFrom = factionSQL.getClaimSQL().of(from);


        if (claimFrom != null) {
            if (claimTo == null) {
                player.sendActionBar(Component.text("§2The Wilderness"));
            } else {
                if (!claimTo.getOwnerFactionName().equals(claimFrom.getOwnerFactionName())) {
                    player.sendActionBar(Component.text(
                            (factionSQL.getClaimSQL().isClaimedByFaction(factionSQL.of(player.getUniqueId()), claimTo.toChunk()) ?
                                    "§a" : "§c") + claimTo.getOwnerFactionName()));
                }
            }
        } else {
            if (claimTo != null) {
                player.sendActionBar(Component.text(
                        (factionSQL.getClaimSQL().isClaimedByFaction(factionSQL.of(player.getUniqueId()), claimTo.toChunk()) ? "§a" :
                                "§c") + claimTo.getOwnerFactionName()));
            }
        }
    }
}

