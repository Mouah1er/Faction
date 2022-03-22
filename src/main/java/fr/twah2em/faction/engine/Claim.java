package fr.twah2em.faction.engine;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;

public class Claim {
    private final String ownerFactionName;
    private final int x;
    private final int z;

    public Claim(String ownerFactionName, int x, int z) {
        this.ownerFactionName = ownerFactionName;
        this.x = Bukkit.getWorlds().get(0).getChunkAt(x, z).getX();
        this.z = Bukkit.getWorlds().get(0).getChunkAt(x, z).getZ();
    }

    public Chunk toChunk() {
        return Bukkit.getWorlds().get(0).getChunkAt(x, z);
    }

    public String getOwnerFactionName() {
        return ownerFactionName;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }
}
