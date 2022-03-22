package fr.twah2em.faction.database;

import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.FactionJavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InvitedPlayersInFactionSQL {
    private static final String TABLE = "invitedPlayersInFaction";

    public void addInvitation(Faction faction, UUID playerUniqueId) {
        faction.getInvitedPlayers().add(playerUniqueId);

        FactionJavaPlugin.getInstance().getSQL().update("INSERT INTO " + TABLE + " (playerUniqueId, factionName) VALUES (" +
                "'" + playerUniqueId + "', '" + faction.getName() + "')");
    }

    public void removeInvitation(Faction faction, UUID playerUniqueId) {
        faction.getInvitedPlayers().remove(playerUniqueId);

        FactionJavaPlugin.getInstance().getSQL().update("DELETE FROM " + TABLE + " WHERE playerUniqueId='" + playerUniqueId + "' " +
                " AND factionName='" + faction.getName() + "'");
    }

    public List<UUID> getPlayers(String factionName) {
        final List<UUID> players = new ArrayList<>();

        FactionJavaPlugin.getInstance().getSQL().query("SELECT * FROM " + TABLE + " WHERE factionName='" + factionName + "'",
                (resultSet) -> {
                    try {
                        while (resultSet.next()) {
                            players.add(UUID.fromString(resultSet.getString("playerUniqueId")));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

        return players;
    }
}
