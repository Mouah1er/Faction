package fr.twah2em.faction.commands.args;

import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.database.FactionSQL;
import org.bukkit.entity.Player;

public class FactionCreate {

    public void send(Player player, String factionName) {
        final FactionSQL factionSQL = FactionJavaPlugin.getInstance().getSQL().getFactionSQL();

        if (factionSQL.hasFaction(player.getUniqueId())) {
            player.sendMessage("§cError: you are already in a faction !");
        } else {
            if (factionSQL.of(factionName) != null) {
                player.sendMessage("§cError: A faction already exists with this name!");
            } else {
                factionSQL.create(new Faction(factionName, player.getUniqueId()));
                player.sendMessage("§aYou have successfully created the §6" + factionName + " §afaction !");
            }
        }
    }
}
