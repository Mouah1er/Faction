package fr.twah2em.faction.database;

import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.engine.ChatTypes;
import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.engine.Roles;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class PlayersInFactionSQL {
    private static final String TABLE = "playersInFaction";

    private final FactionSQL factionSQL;

    public PlayersInFactionSQL(FactionSQL factionSQL) {
        this.factionSQL = factionSQL;
    }

    public void makePlayerJoin(Faction faction, UUID playerUniqueId, String role) {
        faction.getPlayersIn().add(playerUniqueId);

        FactionJavaPlugin.getInstance().getSQL().update("INSERT INTO " + TABLE + " (playerUniqueId, role, chatType, factionName) VALUES (" +
                "'" + playerUniqueId + "', '" + role + "', '" + ChatTypes.PUBLIC.getChatType() + "', '" + faction.getName() + "')");
    }

    public void makePlayerLeave(Faction faction, UUID playerUniqueId) {
        faction.getPlayersIn().remove(playerUniqueId);

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

    public Roles getRole(Faction faction, UUID playerUniqueId) {
        final AtomicReference<Roles> role = new AtomicReference<>();

        FactionJavaPlugin.getInstance().getSQL().query("SELECT * FROM " + TABLE + " WHERE factionName='" + faction.getName() + "' " +
                        "AND playerUniqueId='" + playerUniqueId + "'",
                (resultSet) -> {
                    try {
                        while (resultSet.next()) {
                            role.set(Roles.getByName(resultSet.getString("role")));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

        return role.get();
    }

    public ChatTypes getChatType(Faction faction, UUID playerUniqueId) {
        final AtomicReference<ChatTypes> chatType = new AtomicReference<>();

        FactionJavaPlugin.getInstance().getSQL().query("SELECT * FROM " + TABLE + " WHERE factionName='" + faction.getName() + "' " +
                        "AND playerUniqueId='" + playerUniqueId + "'",
                (resultSet) -> {
                    try {
                        while (resultSet.next()) {
                            chatType.set(ChatTypes.getByName(resultSet.getString("chatType")));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

        return chatType.get();
    }

    public void setChatType(Faction faction, UUID playerUniqueId, ChatTypes chatTypes) {
        FactionJavaPlugin.getInstance().getSQL().update("UPDATE " + TABLE + " SET chatType='"
                + chatTypes.getChatType() + "'" + " WHERE factionName='" + faction.getName() + "' AND playerUniqueId='" + playerUniqueId + "'");
    }

    public FactionSQL getFactionSQL() {
        return factionSQL;
    }
}
