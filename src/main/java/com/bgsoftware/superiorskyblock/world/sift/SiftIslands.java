package com.bgsoftware.superiorskyblock.world.sift;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class SiftIslands {

    private static final Set<UUID> PENDING = new HashSet<>();

    private SiftIslands() {

    }

    public static synchronized void mark(UUID playerUUID) {
        PENDING.add(playerUUID);
    }

    public static synchronized boolean consume(UUID playerUUID) {
        return PENDING.remove(playerUUID);
    }

}
