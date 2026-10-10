package com.bgsoftware.superiorskyblock.config.section;

import com.bgsoftware.superiorskyblock.config.SettingsContainerHolder;

public class ScoreboardSection extends SettingsContainerHolder {

    public boolean isEnabled() {
        return getContainer().scoreboardEnabled;
    }

    public long getUpdateInterval() {
        return getContainer().scoreboardUpdateInterval;
    }

}
