package fr.twah2em.faction.events;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class FactionPlayerChangeChunkEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Chunk from;
    private final Chunk to;

    public FactionPlayerChangeChunkEvent(@NotNull Player player, Chunk from, Chunk to) {
        super(player);

        this.from = from;
        this.to = to;
    }

    public Chunk getFrom() {
        return from;
    }

    public Chunk getTo() {
        return to;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
