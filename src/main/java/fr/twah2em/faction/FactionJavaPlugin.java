package fr.twah2em.faction;

import fr.twah2em.faction.commands.FactionCommandExecutor;
import fr.twah2em.faction.database.SQL;
import fr.twah2em.faction.listeners.FactionListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class FactionJavaPlugin extends JavaPlugin {
    private SQL sql;

    @Override
    public void onEnable() {
        getSLF4JLogger().info("Faction plugin in starting !");

        this.sql = new SQL();

        FactionListener.registerEvents();
        FactionCommandExecutor.registerCommands();
    }

    @Override
    public void onDisable() {
    }

    public static FactionJavaPlugin getInstance() {
        return getPlugin(FactionJavaPlugin.class);
    }

    public SQL getSQL() {
        return sql;
    }
}
