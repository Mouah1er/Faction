package fr.twah2em.faction.commands.args;

import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.database.FactionSQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class FactionDescription {

    public void send(Player player, String[] args) {
        final FactionSQL factionSQL = FactionJavaPlugin.getInstance().getSQL().getFactionSQL();

        final Faction playerFaction = factionSQL.of(player.getUniqueId());

        if (playerFaction == null) {
            player.sendMessage("§cError: You're not in a faction");
        } else {
            if (!playerFaction.getOwner().equals(player.getUniqueId())) {
                player.sendMessage("§cError: You're not the owner of your faction !");
            } else {
                final StringBuilder stringBuilder = new StringBuilder(args.length - 1);

                for (int i = 1; i < args.length; i++) {
                    stringBuilder.append(" ").append(args[i]);
                }

                if (stringBuilder.toString().length() > 255) {
                    player.sendMessage("§cYou can't set the description to more than 255 characters!");
                } else {
                    factionSQL.setDescription(playerFaction, stringBuilder.toString());
                    playerFaction.getPlayersIn()
                            .stream()
                            .map(Bukkit::getPlayer)
                            .filter(Objects::nonNull)
                            .forEach(player1 -> player1.sendMessage("§aThe description of your faction has been set to§6" +
                                    stringBuilder + "§a !"));
                }
            }
        }
    }
}
