package com.bgsoftware.superiorskyblock.world.sift;

import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class SiftIslands {

    private static final String[] BIOME_NAMES = {
            "sift:pink_meadows", "sift:frost_forest", "sift:red_spires", "sift:shattered_peaks",
            "PLAINS", "FOREST", "DESERT", "TAIGA", "JUNGLE", "SAVANNA", "OCEAN"
    };

    private static final Map<UUID, Biome> PENDING = new HashMap<>();

    private SiftIslands() {

    }

    public static synchronized void mark(UUID playerUUID) {
        List<Biome> biomes = new ArrayList<>();
        for (String biomeName : BIOME_NAMES) {
            Biome biome = SuperiorSkyblockPlugin.getPlugin().getNMSAlgorithms().getBiome(biomeName);
            if (biome != null && !biomes.contains(biome))
                biomes.add(biome);
        }

        if (biomes.isEmpty())
            throw new IllegalStateException("No compatible biomes are available for Sift island creation.");

        PENDING.put(playerUUID, biomes.get(ThreadLocalRandom.current().nextInt(biomes.size())));
    }

    public static synchronized Biome consume(UUID playerUUID) {
        return PENDING.remove(playerUUID);
    }

    public static synchronized void discard(UUID playerUUID) {
        PENDING.remove(playerUUID);
    }

}
