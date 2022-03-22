package fr.twah2em.faction.database;

import fr.twah2em.faction.engine.Claim;
import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.engine.Roles;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class FactionSQL {
    private static final String TABLE = "factions";

    private final ClaimSQL claimSQL;
    private final PlayersInFactionSQL playersInFactionSQL;
    private final InvitedPlayersInFactionSQL invitedPlayersInFactionSQL;

    public FactionSQL() {
        this.claimSQL = new ClaimSQL();
        this.playersInFactionSQL = new PlayersInFactionSQL(this);
        this.invitedPlayersInFactionSQL = new InvitedPlayersInFactionSQL();
    }

    public void create(Faction faction) {
        FactionJavaPlugin.getInstance().getSQL().update("INSERT INTO " + TABLE + " (name, creationDate, " +
                "ownerUniqueId, description, power) VALUES ('" + faction.getName() + "', '" + faction.getCreationDate() + "', " +
                "'" + faction.getOwner() + "', '" + faction.getDescription() + "', '" + faction.getPower() + "')");

        playersInFactionSQL.makePlayerJoin(faction, faction.getOwner(), Roles.OWNER.getName());
    }

    public void delete(Faction factionToDelete) {
        FactionJavaPlugin.getInstance().getSQL().update("DELETE FROM " + TABLE + " WHERE name='" + factionToDelete.getName() + "'");

        factionToDelete.getPlayersIn()
                .stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .toList()
                .forEach(player -> {
                    player.sendMessage("§cYour faction has been deleted by the owner !");
                    playersInFactionSQL.makePlayerLeave(factionToDelete, player.getUniqueId());
                });
        factionToDelete.getInvitedPlayers()
                .stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .toList()
                .forEach(player -> {
                    player.sendMessage("§cAs the faction §6" + factionToDelete.getName() + " §chas been deleted," +
                            " your invitation to join has expired.");
                    invitedPlayersInFactionSQL.removeInvitation(factionToDelete, player.getUniqueId());
                });

        factionToDelete.getClaims().forEach(claim -> claimSQL.unClaim(factionToDelete, claim.toChunk()));

        factionToDelete.getPlayersIn().clear();
        factionToDelete.getInvitedPlayers().clear();
        factionToDelete.getClaims().clear();
    }

    public Faction of(UUID playerUniqueId) {
        final AtomicReference<Faction> faction = new AtomicReference<>();

        FactionJavaPlugin.getInstance().getSQL().query("SELECT * FROM playersInFaction WHERE playerUniqueId='" + playerUniqueId + "'",
                (resultSet) -> {
                    try {
                        if (resultSet.next()) {
                            final String name = resultSet.getString("factionName");
                            of(name, faction);
                        } else {
                            faction.set(null);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

        return faction.get();
    }

    public Faction of(String factionName) {
        final AtomicReference<Faction> faction = new AtomicReference<>();

        FactionJavaPlugin.getInstance().getSQL().query("SELECT * FROM " + TABLE + " WHERE name='" + factionName + "'",
                (resultSet) -> {
                    try {
                        if (resultSet.next()) {
                            of(factionName, faction);
                        } else {
                            faction.set(null);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

        return faction.get();
    }

    private void of(String factionName, AtomicReference<Faction> faction) {
        final List<UUID> playersIn = playersInFactionSQL.getPlayers(factionName);
        final List<UUID> invitedPlayers = invitedPlayersInFactionSQL.getPlayers(factionName);
        final List<Claim> claims = claimSQL.getClaims(factionName);
        FactionJavaPlugin.getInstance().getSQL().query("SELECT * FROM " + TABLE + " WHERE name='" + factionName + "'",
                (resultSet) -> {
                    try {
                        if (resultSet.next()) {
                            final String creationDate = resultSet.getString("creationDate");
                            final UUID ownerUniqueId = UUID.fromString(resultSet.getString("ownerUniqueId"));
                            final String description = resultSet.getString("description");
                            final int power = resultSet.getInt("power");

                            faction.set(new Faction(factionName, creationDate, playersIn, invitedPlayers, claims, ownerUniqueId,
                                    description, power));
                        } else {
                            faction.set(null);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void setDescription(Faction faction, String description) {
        faction.setDescription(description);

        FactionJavaPlugin.getInstance().getSQL().update("UPDATE " + TABLE + " SET description='"
                + description.replace("'", "''") + "'" + " WHERE name='" + faction.getName() + "'");
    }

    public void setOwner(Faction faction, UUID playerUniqueId) {
        faction.setOwner(playerUniqueId);

        FactionJavaPlugin.getInstance().getSQL().update("UPDATE " + TABLE + " SET ownerUniqueId='" + playerUniqueId + "' " +
                "WHERE name='" + faction.getName() + "'");
    }

    public boolean hasFaction(UUID uuid) {
        return of(uuid) != null;
    }

    public ClaimSQL getClaimSQL() {
        return claimSQL;
    }

    public PlayersInFactionSQL getPlayersInFactionSQL() {
        return playersInFactionSQL;
    }

    public InvitedPlayersInFactionSQL getInvitedPlayersInFactionSQL() {
        return invitedPlayersInFactionSQL;
    }
}
