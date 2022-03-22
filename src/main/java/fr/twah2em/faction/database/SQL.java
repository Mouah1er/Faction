package fr.twah2em.faction.database;

import fr.twah2em.faction.FactionJavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.function.Consumer;

public class SQL {
    private final FactionSQL factionSQL;

    private Connection connection;

    public SQL() {
        createTables();

        this.factionSQL = new FactionSQL();
    }

    private void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        final File file = new File(FactionJavaPlugin.getInstance().getDataFolder(), "factions.db");

        if (!(file.getParentFile().exists())) file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
        update("CREATE TABLE IF NOT EXISTS factions(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(255), " +
                "creationDate VARCHAR(255), " +
                "ownerUniqueId VARCHAR(37), " +
                "description VARCHAR(255), " +
                "power INTEGER)");

        update("CREATE TABLE IF NOT EXISTS invitedPlayersInFaction(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "playerUniqueId VARCHAR(37), " +
                "factionName VARCHAR(255))");

        update("CREATE TABLE IF NOT EXISTS playersInFaction(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "playerUniqueId VARCHAR(37), " +
                "role VARCHAR(255), " +
                "chatType VARCHAR(255), " +
                "factionName VARCHAR(255))");

        update("CREATE TABLE IF NOT EXISTS claims(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "factionOwnerName VARCHAR(255), " +
                "x BIGINT, " +
                "z BIGINT)");
    }

    public void update(String query) {
        connect();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void query(String query, Consumer<ResultSet> consumer) {
        connect();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            consumer.accept(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public FactionSQL getFactionSQL() {
        return factionSQL;
    }

    public Connection getConnection() {
        return connection;
    }
}
