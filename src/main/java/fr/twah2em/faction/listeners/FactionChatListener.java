package fr.twah2em.faction.listeners;

import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.database.FactionSQL;
import fr.twah2em.faction.engine.ChatTypes;
import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.engine.Roles;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class FactionChatListener implements FactionListener<AsyncPlayerChatEvent> {

    @Override
    @EventHandler
    public void onEvent(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final FactionSQL factionSQL = FactionJavaPlugin.getInstance().getSQL().getFactionSQL();
        final Faction playerFaction = factionSQL.of(player.getUniqueId());

        if (playerFaction != null) {
            final ChatTypes chatType = factionSQL.getPlayersInFactionSQL().getChatType(playerFaction, player.getUniqueId());
            final Roles role = factionSQL.getPlayersInFactionSQL().getRole(playerFaction, player.getUniqueId());

            if (chatType == ChatTypes.FACTION) {
                Bukkit.getOnlinePlayers().forEach(player1 -> {
                    final Faction player1Faction = factionSQL.of(player1.getUniqueId());

                    if (player1Faction != null) {
                        if (player1Faction.getName().equals(playerFaction.getName())) {
                            player1.sendMessage(Component.text("§e[Faction-Chat] " + role.getName() + " " + player.getName()
                                            + " §7: §e")
                                    .append(Component.text(event.getMessage())));
                        }
                    }
                });
            } else if (chatType == ChatTypes.PUBLIC) {
                Bukkit.broadcast(Component.text("§7[" + playerFaction.getName() + "] " + player.getName() + " : §f")
                        .append(Component.text(event.getMessage())));
            } else if (chatType == ChatTypes.ALLIES) {
                // TODO: 24/02/2022 Faire le système d'alliés
            }
        } else {
            Bukkit.broadcast(Component.text("§2[Wilderness] §7" + player.getName() + " : §f")
                    .append(Component.text(event.getMessage())));
        }
        event.setCancelled(true);
    }
}
