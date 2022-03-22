package fr.twah2em.faction.listeners;

import fr.twah2em.faction.events.FactionPlayerChangeChunkEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class FactionPlayerMoveListener implements FactionListener<PlayerMoveEvent> {

    @Override
    @EventHandler
    public void onEvent(PlayerMoveEvent event) {
        if (!event.getFrom().getChunk().equals(event.getTo().getChunk())) {
            Bukkit.getPluginManager().callEvent(new FactionPlayerChangeChunkEvent(event.getPlayer(), event.getFrom().getChunk(),
                    event.getTo().getChunk()));
        }
    }
}
