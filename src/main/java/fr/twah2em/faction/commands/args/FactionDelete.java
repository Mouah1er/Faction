package fr.twah2em.faction.commands.args;

import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.database.FactionSQL;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.entity.Player;

public class FactionDelete {

    public void send(Player player) {
        final FactionSQL factionSQL = FactionJavaPlugin.getInstance().getSQL().getFactionSQL();
        final Faction playerFaction = factionSQL.of(player.getUniqueId());

        if (playerFaction == null) {
            player.sendMessage("§cError: You're not in any faction !");
        } else {
            if (!playerFaction.getOwner().equals(player.getUniqueId())) {
                player.sendMessage("§cError: You're not the owner of your faction !");
            } else {
                player.sendMessage(Component.text("§cAre you very sure that you want to delete your faction ? ")
                        .append(Component.text("§7[§aYes§7]")
                                .clickEvent(ClickEvent.runCommand("/fop delete"))
                                .hoverEvent(HoverEvent.showText(Component.text("§7Click"))))
                        .append(Component.text(" "))
                        .append(Component.text("§7[§cNo§7]")
                                .clickEvent(ClickEvent.runCommand("/fop nodelete"))
                                .hoverEvent(HoverEvent.showText(Component.text("§7Click")))));
            }
        }
    }
}
