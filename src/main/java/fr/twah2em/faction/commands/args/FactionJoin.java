package fr.twah2em.faction.commands.args;

import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.engine.Roles;
import fr.twah2em.faction.database.FactionSQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class FactionJoin {

    public void send(Player player, String factionName) {
        final FactionSQL factionSQL = FactionJavaPlugin.getInstance().getSQL().getFactionSQL();

        if (factionSQL.hasFaction(player.getUniqueId())) {
            player.sendMessage("§cError: You are already in a faction !");
        } else {
            final Faction givenFaction = factionSQL.of(factionName);

            if (givenFaction == null) {
                player.sendMessage("§cError: The faction does not exist !");
            } else {
                if (!givenFaction.getInvitedPlayers().contains(player.getUniqueId())) {
                    player.sendMessage("§cError: You are not invited in this faction !");
                } else {
                    factionSQL.getPlayersInFactionSQL().makePlayerJoin(givenFaction, player.getUniqueId(), Roles.MEMBER.getName());
                    factionSQL.getInvitedPlayersInFactionSQL().removeInvitation(givenFaction, player.getUniqueId());
                    givenFaction.getPlayersIn()
                            .stream()
                            .map(Bukkit::getPlayer)
                            .filter(Objects::nonNull)
                            .forEach(playersIn -> playersIn.sendMessage("§b" + player.getName() + " §ahas joined the faction !"));
                }
            }
        }
    }
}
