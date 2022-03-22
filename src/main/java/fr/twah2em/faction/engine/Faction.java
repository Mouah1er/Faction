package fr.twah2em.faction.engine;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Faction {
    private final String name;
    private final String creationDate;
    private final List<UUID> playersIn;
    private final List<UUID> invitedPlayers;
    private final List<Claim> claims;

    private UUID owner;
    private String description;
    private int power;

    public Faction(String name, String creationDate, List<UUID> playersIn, List<UUID> invitedPlayers, List<Claim> claims,
                   UUID owner, String description, int power) {
        this.name = name;
        this.creationDate = creationDate;
        this.playersIn = new CopyOnWriteArrayList<>(playersIn);
        this.invitedPlayers = new CopyOnWriteArrayList<>(invitedPlayers);
        this.claims = new CopyOnWriteArrayList<>(claims);

        this.owner = owner;
        this.description = description;
        this.power = power;
    }

    public Faction(String name, UUID owner) {
        this(name, new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS").format(new Date()),
                new CopyOnWriteArrayList<>(List.of(owner)), new CopyOnWriteArrayList<>(), new CopyOnWriteArrayList<>(),
                owner, "", 20);
    }

    public String getName() {
        return name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public List<UUID> getPlayersIn() {
        return playersIn;
    }

    public List<UUID> getInvitedPlayers() {
        return invitedPlayers;
    }

    public List<Claim> getClaims() {
        return claims;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID ownerUniqueId) {
        this.owner = ownerUniqueId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
