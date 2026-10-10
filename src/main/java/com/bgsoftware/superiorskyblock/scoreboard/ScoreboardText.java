package com.bgsoftware.superiorskyblock.scoreboard;

import com.bgsoftware.superiorskyblock.core.ServerVersion;
import com.bgsoftware.superiorskyblock.core.formatting.impl.ColorFormatter;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ScoreboardText {

    private static final Pattern GRADIENT_PATTERN =
            Pattern.compile("<gradient:#([0-9A-Fa-f]{6}):#([0-9A-Fa-f]{6})>(.*?)</gradient>");

    private static final Pattern BARE_HEX_PATTERN = Pattern.compile("(?<![&§])\\{HEX:");

    private ScoreboardText() {

    }

    public static String format(String text) {
        String output = ServerVersion.isAtLeast(ServerVersion.v1_16) ?
                applyGradients(text) : GRADIENT_PATTERN.matcher(text).replaceAll("$3");
        output = BARE_HEX_PATTERN.matcher(output).replaceAll("&{HEX:");
        return ColorFormatter.getInstance().format(output);
    }

    private static String applyGradients(String text) {
        Matcher matcher = GRADIENT_PATTERN.matcher(text);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String gradient = gradient(matcher.group(3), matcher.group(1), matcher.group(2));
            matcher.appendReplacement(buffer, Matcher.quoteReplacement(gradient));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private static String gradient(String text, String startHex, String endHex) {
        int[] start = parseHex(startHex);
        int[] end = parseHex(endHex);
        int length = text.length();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            double ratio = length == 1 ? 0D : (double) i / (length - 1);
            builder.append(toLegacyHex(
                    interpolate(start[0], end[0], ratio),
                    interpolate(start[1], end[1], ratio),
                    interpolate(start[2], end[2], ratio)
            )).append(text.charAt(i));
        }

        return builder.toString();
    }

    private static int[] parseHex(String hex) {
        return new int[]{
                Integer.parseInt(hex.substring(0, 2), 16),
                Integer.parseInt(hex.substring(2, 4), 16),
                Integer.parseInt(hex.substring(4, 6), 16)
        };
    }

    private static int interpolate(int from, int to, double ratio) {
        return (int) Math.round(from + (to - from) * ratio);
    }

    private static String toLegacyHex(int red, int green, int blue) {
        StringBuilder builder = new StringBuilder().append(ChatColor.COLOR_CHAR).append('x');
        for (char ch : String.format("%02X%02X%02X", red, green, blue).toCharArray())
            builder.append(ChatColor.COLOR_CHAR).append(ch);
        return builder.toString();
    }

}
