package fr.twah2em.faction.commands;

import fr.twah2em.faction.commands.args.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FactionCommand implements FactionCommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cError: You're not a player !");
        } else {
            if (args.length == 0) {
                sendHelp(player);
            } else {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("help")) {
                        sendHelp(player);
                    } else {
                        if (args[0].equalsIgnoreCase("info")) {
                            new FactionInfo(player, player);
                        } else if (args[0].equalsIgnoreCase("leave")) {
                            new FactionLeave().send(player);
                        } else if (args[0].equalsIgnoreCase("claim")) {
                            new FactionClaim().send(player);
                        } else if (args[0].equalsIgnoreCase("unclaim")) {
                            new FactionUnclaim().send(player);
                        } else if (args[0].equalsIgnoreCase("delete")) {
                            new FactionDelete().send(player);
                        } else if (args[0].equalsIgnoreCase("chat")) {
                          new FactionChat().send(player);
                        } else {
                            player.sendMessage("§cInvalid command ! §7(/f help)");
                        }
                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("create")) {
                        new FactionCreate().send(player, args[1]);
                    } else if (args[0].equalsIgnoreCase("join")) {
                        new FactionJoin().send(player, args[1]);
                    } else if (args[0].equalsIgnoreCase("invite")) {
                        new FactionInvite().send(player, args[1]);
                    } else if (args[0].equalsIgnoreCase("uninvite")) {
                        new FactionUninvite().send(player, args[1]);
                    } else if (args[0].equalsIgnoreCase("kick")) {
                        new FactionKick().send(player, args[1]);
                    } else if (args[0].equalsIgnoreCase("description")) {
                        new FactionDescription().send(player, args);
                    } else if (args[0].equalsIgnoreCase("lead")) {
                        new FactionLead().send(player, args[1]);
                    } else {
                        player.sendMessage("§cInvalid command ! §7(/f help)");
                    }
                } else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("info")) {
                        if (args[1].equalsIgnoreCase("player")) {
                            final OfflinePlayer targetOfflinePlayer = Bukkit.getOfflinePlayerIfCached(args[2]);

                            new FactionInfo(player, targetOfflinePlayer);
                        } else if (args[1].equalsIgnoreCase("faction")) {
                            new FactionInfo(args[2], player);
                        }
                    } else {
                        player.sendMessage("§cInvalid command ! §7(/f help)");
                    }
                } else {
                    if (args[0].equalsIgnoreCase("description")) {
                        new FactionDescription().send(player, args);
                    } else {
                        player.sendMessage("§cInvalid command ! §7(/f help)");
                    }
                }
            }
        }
        return false;
    }

    private void sendHelp(Player player) {
        player.sendMessage("§7§m----------§9 Faction §7§m----------",
                "§9-§6/f help§7: §bShow this message",
                "",
                "§9-§6/f create <name>§7: §bCreate a new faction",
                "§9-§6/f lead <player>§7: Set the lead to the given player",
                "§9-§6/f delete§7: Delete the faction",
                "§9-§6/f description <description>§7: §bSet description of the faction",
                "",
                "§9-§6/f info§7: §bDisplay info of your faction",
                "§9-§6/f info faction <name>§7: §bDisplay info of the given faction",
                "§9-§6/f info player <player>§7: §bDisplay the faction info of the given player",
                "",
                "§9-§6/f invite <player>§7: §bInvite the given player in the faction",
                "§9-§6/f uninvite <player>§7: §bCancel the invitation of the given player",
                "",
                "§9-§6/f claim§7: §bClaim the chunk you are in",
                "§9-§6/f unclaim§7: §bUnclaim the chunk you are in",
                "§9-§6/f chat§7: §bChange the type of chat you use (faction, allies, public)",
                "",
                "§9-§6/f join <name>§7: §bJoin the given faction",
                "§9-§6/f leave§7: §bLeave your current faction",
                "§9-§6/f kick <player>§7: §bKick the player from your faction",
                "§7§m----------§9 Faction §7§m----------");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        final List<String> completions = new ArrayList<>();
        final List<String> commands = new ArrayList<>();

        if (args.length == 1) {
            commands.add("help");
            commands.add("create");
            commands.add("lead");
            commands.add("delete");
            commands.add("description");
            commands.add("info");
            commands.add("invite");
            commands.add("uninvite");
            commands.add("claim");
            commands.add("unclaim");
            commands.add("chat");
            commands.add("join");
            commands.add("leave");
            commands.add("kick");

            StringUtil.copyPartialMatches(args[0], commands, completions);
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("info")) {
                commands.add("faction");
                commands.add("player");
            } else if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("join") ||
                    args[0].equalsIgnoreCase("description")) {
                commands.add("");
            } else if (args[0].equalsIgnoreCase("lead") || args[0].equalsIgnoreCase("invite") ||
                    args[0].equalsIgnoreCase("uninvite") || args[0].equalsIgnoreCase("kick")) {
                Bukkit.getOnlinePlayers().forEach(player -> commands.add(player.getName()));
            }

            StringUtil.copyPartialMatches(args[1], commands, completions);
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("info")) {
                if (args[1].equalsIgnoreCase("player")) {
                    Bukkit.getOnlinePlayers().forEach(player -> commands.add(player.getName()));
                } else if (args[1].equalsIgnoreCase("faction")) {
                    commands.add("");
                }
            }

            StringUtil.copyPartialMatches(args[2], commands, completions);
        }

        return completions;
    }
}
