package fr.twah2em.faction.listeners;

import fr.twah2em.faction.FactionJavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public interface FactionListener<E extends Event> extends Listener {

    void onEvent(E event);

    static void registerEvents() {
        final Reflections reflections = new Reflections(new ConfigurationBuilder().forPackage("fr.twah2em.faction"));

        final Set<Class<? extends FactionListener>> subTypes = reflections.getSubTypesOf(FactionListener.class);
        subTypes.forEach(subClass -> {
            try {
                Bukkit.getPluginManager().registerEvents(subClass.getConstructor().newInstance(), FactionJavaPlugin.getInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }
}
