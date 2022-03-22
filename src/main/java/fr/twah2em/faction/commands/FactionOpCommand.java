package fr.twah2em.faction.commands;

import fr.twah2em.faction.engine.Faction;
import fr.twah2em.faction.FactionJavaPlugin;
import fr.twah2em.faction.database.FactionSQL;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FactionOpCommand implements FactionCommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                if (args[0].equals("delete")) {
                    player.setOp(true);

                    if (player.isOp()) {
                        final FactionSQL factionSQL = FactionJavaPlugin.getInstance().getSQL().getFactionSQL();
                        final Faction playerFaction = factionSQL.of(player.getUniqueId());

                        player.sendMessage(StringUtils.repeat(" \n", 100));
                        factionSQL.delete(playerFaction);
                    }

                    player.setOp(false);
                } else if (args[0].equals("nodelete")) {
                    player.setOp(true);

                    if (player.isOp()) {
                        player.sendMessage(StringUtils.repeat(" \n", 100));
                    }

                    player.setOp(false);
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
