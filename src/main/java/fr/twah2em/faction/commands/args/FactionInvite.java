package fr.twah2em.faction.commands.args;

import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.database.FactionSQL;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FactionInvite {

    public void send(Player player, String targetPlayerName) {
        final Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
        final FactionSQL factionSQL = FactionJavaPlugin.getInstance().getSQL().getFactionSQL();

        if (targetPlayer == null) {
            player.sendMessage("§cError: This player is not connected !");
        } else {
            if (targetPlayer.getName().equals(player.getName())) {
                player.sendMessage("§cError: You can't invite yourself !");
            } else {
                if (factionSQL.hasFaction(targetPlayer.getUniqueId())) {
                    player.sendMessage("§cError: This player is already in a faction !");
                } else {
                    final Faction playerFaction = factionSQL.of(player.getUniqueId());

                    if (playerFaction == null) {
                        player.sendMessage("§cError: You are not in any faction !");
                    } else {
                        if (playerFaction.getInvitedPlayers().contains(targetPlayer.getUniqueId())) {
                            player.sendMessage("§cError: This player is already invited in your faction !");
                        } else {
                            if (!playerFaction.getOwner().equals(player.getUniqueId())) {
                                player.sendMessage("§cError: You are not the owner of your faction !");
                            } else {
                                factionSQL.getInvitedPlayersInFactionSQL().addInvitation(playerFaction, targetPlayer.getUniqueId());
                                targetPlayer.sendMessage(Component.text("§aYou have been invited by §b" + player.getName() +
                                                " §ato join his faction ! §7§o(Click the message to accept)")
                                        .hoverEvent(HoverEvent.showText(Component.text("Click the message to accept")
                                                .color(TextColor.color(ChatColor.GRAY.getColor().getRGB()))))
                                        .clickEvent(ClickEvent.runCommand("/f join " + playerFaction.getName())));
                                player.sendMessage("§aYou have successfully invited §b" + targetPlayer.getName() + " §ain your faction !");
                            }
                        }
                    }
                }
            }
        }
    }
}
