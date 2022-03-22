package fr.twah2em.faction.database;

import fr.twah2em.faction.engine.Claim;
import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.FactionJavaPlugin;
import org.bukkit.Chunk;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ClaimSQL {
    private static final String TABLE = "claims";

    public void claim(Faction faction, Chunk chunk) {
        final List<Claim> claims = faction.getClaims();
        claims.add(new Claim(faction.getName(), chunk.getX(), chunk.getZ()));

        FactionJavaPlugin.getInstance().getSQL().update("INSERT INTO " + TABLE + " (factionOwnerName, x, z) VALUES (" +
                "'" + faction.getName() + "', '" + chunk.getX() + "', '" + chunk.getZ() + "')");
    }

    public void unClaim(Faction faction, Chunk chunk) {
        faction.getClaims().removeIf(claim -> claim.getOwnerFactionName().equals(faction.getName()) &&
                claim.getX() == chunk.getX() && claim.getZ() == chunk.getZ());

        FactionJavaPlugin.getInstance().getSQL().update("DELETE FROM " + TABLE + " WHERE factionOwnerName='" + faction.getName() + "'" +
                " AND x='" + chunk.getX() + "' AND z='" + chunk.getZ() + "'");
    }

    public List<Claim> getClaims(String factionName) {
        final List<Claim> claims = new ArrayList<>();

        FactionJavaPlugin.getInstance().getSQL().query("SELECT * FROM " + TABLE + " WHERE factionOwnerName='" + factionName + "'",
                (resultSet) -> {
                    try {
                        while (resultSet.next()) {
                            final int x = resultSet.getInt("x");
                            final int z = resultSet.getInt("z");

                            claims.add(new Claim(factionName, x, z));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

        return claims;
    }

    public List<Claim> getClaims(Faction faction) {
        return getClaims(faction.getName());
    }

    public Claim of(Chunk chunk) {
        final AtomicReference<Claim> claim = new AtomicReference<>();

        FactionJavaPlugin.getInstance().getSQL().query("SELECT * FROM " + TABLE + " " +
                "WHERE x='" + chunk.getX() + "' AND z='" + chunk.getZ() + "'", (resultSet) -> {
            try {
                if (resultSet.next()) {
                    final String name = resultSet.getString("factionOwnerName");

                    claim.set(new Claim(name, chunk.getX(), chunk.getZ()));
                } else {
                    claim.set(null);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return claim.get();
    }

    public boolean isClaimedByFaction(Faction faction, Chunk chunk) {
        return faction.getClaims()
                .stream()
                .map(Claim::toChunk)
                .toList()
                .contains(chunk);
    }
}
