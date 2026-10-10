package com.bgsoftware.superiorskyblock.config.section;

import com.bgsoftware.common.annotations.Nullable;
import com.bgsoftware.superiorskyblock.api.config.SettingsManager;
import com.bgsoftware.superiorskyblock.config.SettingsContainerHolder;
import org.bukkit.Material;

import java.util.List;

public class SpawnSection extends SettingsContainerHolder implements SettingsManager.Spawn {

    @Override
    public String getLocation() {
        return getContainer().spawnLocation;
    }

    @Override
    public boolean isProtected() {
        return getContainer().spawnProtection;
    }

    @Override
    public List<String> getSettings() {
        return getContainer().spawnSettings;
    }

    @Override
    public List<String> getPermissions() {
        return getContainer().spawnPermissions;
    }

    @Override
    public boolean isWorldBorder() {
        return getContainer().spawnWorldBorder;
    }

    @Override
    public int getSize() {
        return getContainer().spawnSize;
    }

    @Override
    public boolean isPlayersDamage() {
        return getContainer().spawnDamage;
    }

    @Nullable
    public Material getPortalBlock() {
        return getContainer().spawnPortalBlock;
    }

}
