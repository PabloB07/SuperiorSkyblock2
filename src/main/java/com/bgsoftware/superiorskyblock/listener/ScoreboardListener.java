package com.bgsoftware.superiorskyblock.listener;

import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.platform.event.GameEvent;
import com.bgsoftware.superiorskyblock.platform.event.GameEventPriority;
import com.bgsoftware.superiorskyblock.platform.event.GameEventType;
import com.bgsoftware.superiorskyblock.platform.event.args.GameEventArgs;

public class ScoreboardListener extends AbstractGameEventListener {

    public ScoreboardListener(SuperiorSkyblockPlugin plugin) {
        super(plugin);
        registerCallback(GameEventType.PLAYER_JOIN_EVENT, GameEventPriority.MONITOR, this::onPlayerJoin);
        registerCallback(GameEventType.PLAYER_QUIT_EVENT, GameEventPriority.MONITOR, this::onPlayerQuit);
        plugin.getScoreboards().start();
    }

    private void onPlayerJoin(GameEvent<GameEventArgs.PlayerJoinEvent> e) {
        plugin.getScoreboards().addBoard(e.getArgs().player);
    }

    private void onPlayerQuit(GameEvent<GameEventArgs.PlayerQuitEvent> e) {
        plugin.getScoreboards().removeBoard(e.getArgs().player);
    }

}
