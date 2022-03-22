package fr.twah2em.faction.engine;

import java.util.HashMap;
import java.util.Map;

public enum ChatTypes {
    PUBLIC("Public"),
    ALLIES("Allies"),
    FACTION("Faction"),

    ;

    private static final Map<String, ChatTypes> BY_NAME = new HashMap<>();
    private static final ChatTypes[] values = values();

    static {
        for (ChatTypes value : ChatTypes.values()) {
            BY_NAME.put(value.getChatType(), value);
        }
    }

    private final String chatType;

    ChatTypes(String chatMethod) {
        this.chatType = chatMethod;
    }

    public static ChatTypes getByName(String name) {
        return BY_NAME.get(name);
    }

    public String getChatType() {
        return chatType;
    }

    public ChatTypes nextChatType() {
        return values[(this.ordinal() + 1) % values.length];
    }
}
