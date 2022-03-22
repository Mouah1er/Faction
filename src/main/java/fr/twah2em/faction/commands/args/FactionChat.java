package fr.twah2em.faction.commands.args;

import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.database.FactionSQL;
import fr.twah2em.faction.engine.ChatTypes;
import fr.twah2em.faction.engine.Faction;
import org.bukkit.entity.Player;

public class FactionChat {

    public void send(Player player) {
        final FactionSQL factionSQL = FactionJavaPlugin.getInstance().getSQL().getFactionSQL();
        final Faction playerFaction = factionSQL.of(player.getUniqueId());

        if (playerFaction == null) {
            player.sendMessage("§cError: You can't change your chat type because you don't have any faction !");
        } else {
            final ChatTypes nextChatType = factionSQL.getPlayersInFactionSQL().getChatType(playerFaction, player.getUniqueId()).nextChatType();

            factionSQL.getPlayersInFactionSQL().setChatType(playerFaction, player.getUniqueId(), nextChatType);
            player.sendMessage("§aYour chat type has been set to " + nextChatType.getChatType() + " !");
        }
    }
}
