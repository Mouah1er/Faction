package fr.twah2em.faction.engine;

import java.util.HashMap;
import java.util.Map;

public enum Roles {
    MEMBER("§7Member"),
    OFFICER("§dOfficer"),
    OWNER("§cOwner"),

    ;

    private static final Map<String, Roles> BY_NAME = new HashMap<>();

    static {
        for (Roles value : Roles.values()) {
            BY_NAME.put(value.getName(), value);
        }
    }

    private final String name;

    Roles(String name) {
        this.name = name;
    }

    public static Roles getByName(String name) {
        return BY_NAME.get(name);
    }

    public String getName() {
        return name;
    }
}
