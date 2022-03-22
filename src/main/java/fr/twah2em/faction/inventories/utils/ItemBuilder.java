package fr.twah2em.faction.inventories.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {
    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        this(new ItemStack(material, amount));
    }

    public ItemBuilder(Material material) {
        this(new ItemStack(material));
    }

    public ItemBuilder name(String name) {
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder lore(String... lines) {
        return this.lore(Arrays.stream(lines).toList());
    }

    public ItemBuilder lore(List<String> lines) {
        itemMeta.setLore(lines);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder head(OfflinePlayer targetPlayerHead) {
        final SkullMeta meta = (SkullMeta) this.itemMeta;

        meta.setOwningPlayer(targetPlayerHead);
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder skullTexture(String base64) {
        final SkullMeta skullMeta = (SkullMeta) this.itemMeta;

        try {
            final Method profileMethod = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            profileMethod.setAccessible(true);
            profileMethod.invoke(skullMeta, gameProfile(base64));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            try {
                final Field profileField = skullMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(skullMeta, gameProfile(base64));
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }

        itemStack.setItemMeta(skullMeta);

        return this;
    }

    private GameProfile gameProfile(String base64) {
        final UUID uuid = new UUID(base64.substring(base64.length() - 20).hashCode(), base64.substring(base64.length() - 10).hashCode());
        final GameProfile profile = new GameProfile(uuid, "Player");

        profile.getProperties().put("textures", new Property("textures", base64));

        return profile;
    }

    public ItemStack build() {
        return itemStack;
    }
}
