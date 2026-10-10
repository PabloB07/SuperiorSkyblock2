package com.bgsoftware.superiorskyblock.world.sift;

import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.api.config.SettingsManager;
import com.bgsoftware.superiorskyblock.api.world.Dimension;
import com.bgsoftware.superiorskyblock.core.logging.Log;
import com.bgsoftware.superiorskyblock.world.Dimensions;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public final class SiftWorld {

    public static final String WORLD_NAME = "sift";
    private static final String DATAPACK_NAME = "sift";
    private static final String RESOURCE_ROOT = "sift/datapack/";

    private static final String[] DATAPACK_FILES = {
            "pack.mcmeta",
            "data/sift/dimension/sift.json",
            "data/sift/dimension_type/sift.json",
            "data/sift/worldgen/biome/pink_meadows.json",
            "data/sift/worldgen/biome/frost_forest.json",
            "data/sift/worldgen/biome/red_spires.json",
            "data/sift/worldgen/biome/shattered_peaks.json"
    };

    private SiftWorld() {

    }

    public static void load(SuperiorSkyblockPlugin plugin) {
        Dimension dimension = Dimensions.findByName(WORLD_NAME);
        if (dimension == null)
            return;

        SettingsManager.Worlds.DimensionConfig config = plugin.getSettings().getWorlds().getDimensionConfig(dimension);
        if (config == null || !config.isEnabled())
            return;

        World world = Bukkit.getWorld(WORLD_NAME);

        if (world == null) {
            File worldFolder = new File(Bukkit.getWorldContainer(), WORLD_NAME);
            if (!copyDatapack(plugin, new File(worldFolder, "datapacks/" + DATAPACK_NAME)))
                return;

            world = createWorld();
        }

        if (world == null) {
            Log.error("Could not create the Sift world.");
            return;
        }

        plugin.getGrid().registerIslandWorld(world);
    }

    // WorldCreator(String, NamespacedKey) only exists on 1.16+ APIs, so it is resolved through reflection
    // to keep the main sourceSet compiling against the 1.8 API.
    private static World createWorld() {
        try {
            Class<?> keyClass = Class.forName("org.bukkit.NamespacedKey");
            Object key = keyClass.getConstructor(String.class, String.class).newInstance("sift", "sift");

            Class<?> creatorClass = Class.forName("org.bukkit.WorldCreator");
            // Only the key may be specified; the world's name comes from the key's value.
            Object creator = creatorClass.getConstructor(keyClass).newInstance(key);
            creator = creatorClass.getMethod("environment", World.Environment.class).invoke(creator, World.Environment.NORMAL);
            return (World) creatorClass.getMethod("createWorld").invoke(creator);
        } catch (ReflectiveOperationException error) {
            Log.error(error, "An unexpected error occurred while creating the Sift world:");
            return null;
        }
    }

    private static boolean copyDatapack(SuperiorSkyblockPlugin plugin, File destination) {
        for (String file : DATAPACK_FILES) {
            File target = new File(destination, file);
            try (InputStream resource = plugin.getResource(RESOURCE_ROOT + file)) {
                if (resource == null) {
                    Log.error("Missing Sift datapack resource: ", file);
                    return false;
                }
                //noinspection ResultOfMethodCallIgnored
                target.getParentFile().mkdirs();
                Files.copy(resource, target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException error) {
                Log.error(error, "An unexpected error occurred while copying the Sift datapack:");
                return false;
            }
        }
        return true;
    }

}

