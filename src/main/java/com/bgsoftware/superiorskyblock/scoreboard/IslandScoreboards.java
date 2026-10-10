package com.bgsoftware.superiorskyblock.scoreboard;

import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.bgsoftware.superiorskyblock.config.section.ScoreboardSection;
import com.bgsoftware.superiorskyblock.player.PlayerLocales;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class IslandScoreboards {

    private static final String TITLE_KEY = "SCOREBOARD_TITLE";
    private static final String LINES_KEY = "SCOREBOARD_LINES";

    private final SuperiorSkyblockPlugin plugin;
    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private final Map<Locale, LocaleTexts> texts = new HashMap<>();

    private BukkitTask updateTask;

    public IslandScoreboards(SuperiorSkyblockPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadLocale(Locale locale, YamlConfiguration langConfig) {
        String title = langConfig.getString(TITLE_KEY);
        List<String> lines = langConfig.getStringList(LINES_KEY);
        if (title == null || lines.isEmpty())
            return;

        texts.put(locale, new LocaleTexts(title, lines));
    }

    public void start() {
        stop();

        ScoreboardSection settings = plugin.getSettings().getScoreboard();
        if (!settings.isEnabled())
            return;

        for (Player player : Bukkit.getOnlinePlayers())
            addBoard(player);

        long interval = settings.getUpdateInterval();
        this.updateTask = Bukkit.getScheduler().runTaskTimer(plugin, this::updateBoards, interval, interval);
    }

    public void stop() {
        if (this.updateTask != null) {
            this.updateTask.cancel();
            this.updateTask = null;
        }

        boards.values().forEach(FastBoard::delete);
        boards.clear();
    }

    public void addBoard(Player player) {
        if (!plugin.getSettings().getScoreboard().isEnabled() || boards.containsKey(player.getUniqueId()))
            return;

        boards.put(player.getUniqueId(), new FastBoard(player));
    }

    public void removeBoard(Player player) {
        FastBoard board = boards.remove(player.getUniqueId());
        if (board != null)
            board.delete();
    }

    private void updateBoards() {
        Iterator<FastBoard> iterator = boards.values().iterator();
        while (iterator.hasNext()) {
            FastBoard board = iterator.next();
            Player player = board.getPlayer();

            if (player == null || !player.isOnline()) {
                board.delete();
                iterator.remove();
                continue;
            }

            SuperiorPlayer superiorPlayer = plugin.getPlayers().getSuperiorPlayer(player);
            LocaleTexts localeTexts = getTexts(superiorPlayer.getUserLocale());
            if (localeTexts == null)
                continue;

            Island island = superiorPlayer.getIsland();

            board.updateTitle(ScoreboardText.format(replacePlaceholders(localeTexts.title, player, island)));

            List<String> lines = new ArrayList<>();
            for (String line : localeTexts.lines)
                lines.add(ScoreboardText.format(replacePlaceholders(line, player, island)));
            board.updateLines(lines.toArray(new String[0]));
        }
    }

    private LocaleTexts getTexts(Locale locale) {
        LocaleTexts localeTexts = texts.get(locale);

        if (localeTexts == null)
            localeTexts = texts.get(PlayerLocales.getDefaultLocale());

        if (localeTexts == null)
            localeTexts = texts.get(PlayerLocales.getLocale("en-US"));

        return localeTexts;
    }

    private static String replacePlaceholders(String text, Player player, Island island) {
        return text
                .replace("{player}", player.getName())
                .replace("{online}", String.valueOf(Bukkit.getOnlinePlayers().size()))
                .replace("{island_name}", island == null ? "-" : island.getName())
                .replace("{island_level}", island == null ? "-" : format(island.getIslandLevel()))
                .replace("{island_worth}", island == null ? "-" : format(island.getWorth()));
    }

    private static String format(BigDecimal value) {
        return value.stripTrailingZeros().toPlainString();
    }

    private static class LocaleTexts {

        private final String title;
        private final List<String> lines;

        private LocaleTexts(String title, List<String> lines) {
            this.title = title;
            this.lines = lines;
        }

    }

}
