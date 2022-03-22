package fr.twah2em.faction.commands;

import fr.twah2em.faction.FactionJavaPlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public interface FactionCommandExecutor extends CommandExecutor, TabCompleter {

    static void registerCommands() {
        final Reflections reflections = new Reflections(new ConfigurationBuilder().forPackage("fr.twah2em.faction"));

        final Set<Class<? extends FactionCommandExecutor>> subTypes = reflections.getSubTypesOf(FactionCommandExecutor.class);
        subTypes.forEach(subClass -> {
            try {
                final PluginCommand command = Objects.requireNonNull(FactionJavaPlugin.getInstance().getCommand(subClass.getSimpleName()
                        .toLowerCase(Locale.ROOT)
                        .replace("command", "")));
                command.setExecutor(subClass.getConstructor().newInstance());
                command.setTabCompleter(subClass.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }
}