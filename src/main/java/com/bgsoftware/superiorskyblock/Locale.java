package com.bgsoftware.superiorskyblock;

import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.bgsoftware.superiorskyblock.config.CommentedConfiguration;
import com.bgsoftware.superiorskyblock.config.LangComments;
import com.bgsoftware.superiorskyblock.utils.threads.Executor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public enum Locale {

    ADMIN_DEPOSIT_MONEY,
    ADMIN_DEPOSIT_MONEY_NAME,
    ADMIN_HELP_FOOTER,
    ADMIN_HELP_HEADER,
    ADMIN_HELP_LINE,
    ADMIN_HELP_NEXT_PAGE,
    ALREADY_IN_ISLAND,
    ALREADY_IN_ISLAND_OTHER,
    BANNED_FROM_ISLAND,
    BAN_ANNOUNCEMENT,
    BAN_PLAYERS_WITH_LOWER_ROLE,
    BLOCK_VALUE,
    BLOCK_VALUE_WORTHLESS,
    BONUS_SET_SUCCESS,
    BORDER_PLAYER_COLOR_UPDATED,
    BUILD_OUTSIDE_ISLAND,
    CANNOT_SET_ROLE,
    CHANGED_BIOME,
    CHANGED_CROP_GROWTH,
    CHANGED_CROP_GROWTH_NAME,
    CHANGED_DISCORD,
    CHANGED_HOPPERS_LIMIT,
    CHANGED_HOPPERS_LIMIT_NAME,
    CHANGED_ISLAND_SIZE,
    CHANGED_ISLAND_SIZE_NAME,
    CHANGED_MOB_DROPS,
    CHANGED_MOB_DROPS_NAME,
    CHANGED_NAME,
    CHANGED_NAME_OTHER,
    CHANGED_NAME_OTHER_NAME,
    CHANGED_PAYPAL,
    CHANGED_SPAWNER_RATES,
    CHANGED_SPAWNER_RATES_NAME,
    CHANGED_TEAM_LIMIT,
    CHANGED_TEAM_LIMIT_NAME,
    CHANGED_TELEPORT_LOCATION,
    CHANGED_WARPS_LIMIT,
    CHANGED_WARPS_LIMIT_NAME,
    CHANGE_PERMISSION_FOR_HIGHER_ROLE,
    COMMAND_DESCRIPTION_ACCEPT,
    COMMAND_DESCRIPTION_ADMIN,
    COMMAND_DESCRIPTION_ADMIN_BONUS,
    COMMAND_DESCRIPTION_ADMIN_BYPASS,
    COMMAND_DESCRIPTION_ADMIN_CLOSE,
    COMMAND_DESCRIPTION_ADMIN_DEMOTE,
    COMMAND_DESCRIPTION_ADMIN_DEPOSIT,
    COMMAND_DESCRIPTION_ADMIN_DISBAND,
    COMMAND_DESCRIPTION_ADMIN_GIVE_DISBANDS,
    COMMAND_DESCRIPTION_ADMIN_JOIN,
    COMMAND_DESCRIPTION_ADMIN_MSG,
    COMMAND_DESCRIPTION_ADMIN_MSG_ALL,
    COMMAND_DESCRIPTION_ADMIN_NAME,
    COMMAND_DESCRIPTION_ADMIN_OPEN,
    COMMAND_DESCRIPTION_ADMIN_PROMOTE,
    COMMAND_DESCRIPTION_ADMIN_RELOAD,
    COMMAND_DESCRIPTION_ADMIN_SAVE,
    COMMAND_DESCRIPTION_ADMIN_SCHEMATIC,
    COMMAND_DESCRIPTION_ADMIN_SET_CROP_GROWTH,
    COMMAND_DESCRIPTION_ADMIN_SET_DISBANDS,
    COMMAND_DESCRIPTION_ADMIN_SET_HOPPERS_LIMIT,
    COMMAND_DESCRIPTION_ADMIN_SET_LEADER,
    COMMAND_DESCRIPTION_ADMIN_SET_MOB_DROPS,
    COMMAND_DESCRIPTION_ADMIN_SET_SIZE,
    COMMAND_DESCRIPTION_ADMIN_SET_SPAWNER_RATES,
    COMMAND_DESCRIPTION_ADMIN_SET_TEAM_LIMIT,
    COMMAND_DESCRIPTION_ADMIN_SET_UPGRADE,
    COMMAND_DESCRIPTION_ADMIN_SET_WARPS_LIMIT,
    COMMAND_DESCRIPTION_ADMIN_SPY,
    COMMAND_DESCRIPTION_ADMIN_WITHDRAW,
    COMMAND_DESCRIPTION_BAN,
    COMMAND_DESCRIPTION_BIOME,
    COMMAND_DESCRIPTION_BORDER,
    COMMAND_DESCRIPTION_CLOSE,
    COMMAND_DESCRIPTION_CREATE,
    COMMAND_DESCRIPTION_DEL_WARP,
    COMMAND_DESCRIPTION_DEMOTE,
    COMMAND_DESCRIPTION_DEPOSIT,
    COMMAND_DESCRIPTION_DISBAND,
    COMMAND_DESCRIPTION_EXPEL,
    COMMAND_DESCRIPTION_FLY,
    COMMAND_DESCRIPTION_HELP,
    COMMAND_DESCRIPTION_INVITE,
    COMMAND_DESCRIPTION_KICK,
    COMMAND_DESCRIPTION_LEAVE,
    COMMAND_DESCRIPTION_NAME,
    COMMAND_DESCRIPTION_OPEN,
    COMMAND_DESCRIPTION_PANEL,
    COMMAND_DESCRIPTION_PARDON,
    COMMAND_DESCRIPTION_PERMISSIONS,
    COMMAND_DESCRIPTION_PROMOTE,
    COMMAND_DESCRIPTION_RANKUP,
    COMMAND_DESCRIPTION_RECALC,
    COMMAND_DESCRIPTION_SET_DISCORD,
    COMMAND_DESCRIPTION_SET_PAYPAL,
    COMMAND_DESCRIPTION_SET_PERMISSION,
    COMMAND_DESCRIPTION_SET_ROLE,
    COMMAND_DESCRIPTION_SET_TELEPORT,
    COMMAND_DESCRIPTION_SET_WARP,
    COMMAND_DESCRIPTION_SHOW,
    COMMAND_DESCRIPTION_TEAM,
    COMMAND_DESCRIPTION_TEAM_CHAT,
    COMMAND_DESCRIPTION_TELEPORT,
    COMMAND_DESCRIPTION_TOGGLE,
    COMMAND_DESCRIPTION_TOP,
    COMMAND_DESCRIPTION_TRANSFER,
    COMMAND_DESCRIPTION_UPGRADE,
    COMMAND_DESCRIPTION_VALUE,
    COMMAND_DESCRIPTION_WARP,
    COMMAND_DESCRIPTION_WARPS,
    COMMAND_DESCRIPTION_WITHDRAW,
    COMMAND_USAGE,
    CREATE_ISLAND,
    DELETE_WARP,
    DEMOTED_MEMBER,
    DEMOTE_PLAYERS_WITH_LOWER_ROLE,
    DEPOSIT_ANNOUNCEMENT,
    DESTROY_OUTSIDE_ISLAND,
    DISBANDED_ISLAND,
    DISBANDED_ISLAND_OTHER,
    DISBANDED_ISLAND_OTHER_NAME,
    DISBAND_ANNOUNCEMENT,
    DISBAND_GIVE,
    DISBAND_GIVE_OTHER,
    DISBAND_SET,
    DISBAND_SET_OTHER,
    EXPELLED_PLAYER,
    GLOBAL_MESSAGE_SENT,
    GLOBAL_MESSAGE_SENT_NAME,
    GOT_BANNED,
    GOT_DEMOTED,
    GOT_EXPELLED,
    GOT_INVITE,
    GOT_KICKED,
    GOT_PROMOTED,
    GOT_REVOKED,
    GOT_UNBANNED,
    HIT_PLAYER_IN_ISLAND,
    INVALID_AMOUNT,
    INVALID_BOOLEAN,
    INVALID_BORDER_COLOR,
    INVALID_ISLAND,
    INVALID_ISLAND_OTHER,
    INVALID_ISLAND_OTHER_NAME,
    INVALID_ISLAND_PERMISSION,
    INVALID_ITEM_IN_HAND,
    INVALID_LEVEL,
    INVALID_LIMIT,
    INVALID_MULTIPLIER,
    INVALID_PLAYER,
    INVALID_ROLE,
    INVALID_SIZE,
    INVALID_TOGGLE_MODE,
    INVALID_UPGRADE,
    INVALID_WARP,
    INVITE_ANNOUNCEMENT,
    INVITE_BANNED_PLAYER,
    INVITE_TO_FULL_ISLAND,
    ISLAND_ALREADY_EXIST,
    ISLAND_BANK_EMPTY,
    ISLAND_CALC_ANNOUNCEMENT,
    ISLAND_CLOSED,
    ISLAND_CREATE_PROCCESS_REQUEST,
    ISLAND_FLY_DISABLED,
    ISLAND_FLY_ENABLED,
    ISLAND_GOT_DELETED_WHILE_INSIDE,
    ISLAND_HELP_FOOTER,
    ISLAND_HELP_HEADER,
    ISLAND_HELP_LINE,
    ISLAND_HELP_NEXT_PAGE,
    ISLAND_INFO_ADMINS,
    ISLAND_INFO_BANK,
    ISLAND_INFO_DISCORD,
    ISLAND_INFO_FOOTER,
    ISLAND_INFO_HEADER,
    ISLAND_INFO_LOCATION,
    ISLAND_INFO_MEMBERS,
    ISLAND_INFO_MODS,
    ISLAND_INFO_NAME,
    ISLAND_INFO_OWNER,
    ISLAND_INFO_PAYPAL,
    ISLAND_INFO_PLAYER_LINE,
    ISLAND_INFO_WORTH,
    ISLAND_OPENED,
    ISLAND_PROTECTED,
    ISLAND_SAVE_ANNOUNCEMENT,
    ISLAND_TEAM_STATUS_ADMINS,
    ISLAND_TEAM_STATUS_FOOTER,
    ISLAND_TEAM_STATUS_HEADER,
    ISLAND_TEAM_STATUS_LEADER,
    ISLAND_TEAM_STATUS_MEMBERS,
    ISLAND_TEAM_STATUS_MODS,
    ISLAND_TEAM_STATUS_OFFLINE,
    ISLAND_TEAM_STATUS_ONLINE,
    ISLAND_WARP_PUBLIC,
    ISLAND_WARP_PRIVATE,
    ISLAND_WAS_CLOSED,
    ISLAND_WORTH_RESULT,
    JOINED_ISLAND,
    JOINED_ISLAND_NAME,
    JOIN_ANNOUNCEMENT,
    JOIN_FULL_ISLAND,
    JOIN_WHILE_IN_ISLAND,
    KICK_ANNOUNCEMENT,
    KICK_PLAYERS_WITH_LOWER_ROLE,
    LACK_CHANGE_PERMISSION,
    LAST_ROLE_DEMOTE,
    LAST_ROLE_PROMOTE,
    LEAVE_ANNOUNCEMENT,
    LEAVE_ISLAND_AS_LEADER,
    LEFT_ISLAND,
    MAXIMUM_LEVEL,
    MESSAGE_SENT,
    NAME_ANNOUNCEMENT,
    NAME_BLACKLISTED,
    NAME_CHAT_FORMAT,
    NAME_TOO_LONG,
    NAME_TOO_SHORT,
    NOT_ENOUGH_MONEY_TO_DEPOSIT,
    NOT_ENOUGH_MONEY_TO_UPGRADE,
    NO_BAN_PERMISSION,
    NO_CLOSE_BYPASS,
    NO_CLOSE_PERMISSION,
    NO_COMMAND_PERMISSION,
    NO_DELETE_WARP_PERMISSION,
    NO_DEMOTE_PERMISSION,
    NO_DEPOSIT_PERMISSION,
    NO_DISBAND_PERMISSION,
    NO_EXPEL_PERMISSION,
    NO_INVITE_PERMISSION,
    NO_ISLAND_INVITE,
    NO_KICK_PERMISSION,
    NO_MORE_DISBANDS,
    NO_MORE_WARPS,
    NO_NAME_PERMISSION,
    NO_OPEN_PERMISSION,
    NO_PERMISSION_CHECK_PERMISSION,
    NO_PERMISSION_SET_PERMISSION,
    NO_PROMOTE_PERMISSION,
    NO_RANKUP_PERMISSION,
    NO_SET_BIOME_PERMISSION,
    NO_SET_DISCORD_PERMISSION,
    NO_SET_HOME_PERMISSION,
    NO_SET_PAYPAL_PERMISSION,
    NO_SET_ROLE_PERMISSION,
    NO_SET_WARP_PERMISSION,
    NO_TRANSFER_PERMISSION,
    NO_WITHDRAW_PERMISSION,
    PANEL_TOGGLE_OFF,
    PANEL_TOGGLE_ON,
    PERMISSION_CHECK,
    PERMISSION_SPACER,
    PLAYER_ALREADY_BANNED,
    PLAYER_ALREADY_IN_ROLE,
    PLAYER_EXPEL_BYPASS,
    PLAYER_JOIN_ANNOUNCEMENT,
    PLAYER_NOT_BANNED,
    PLAYER_NOT_INSIDE_ISLAND,
    PLAYER_NOT_ONLINE,
    PLAYER_QUIT_ANNOUNCEMENT,
    PROMOTED_MEMBER,
    PROMOTE_PLAYERS_WITH_LOWER_ROLE,
    REACHED_HOPPERS_LIMIT,
    RECALC_PROCCESS_REQUEST,
    RELOAD_COMPLETED,
    RELOAD_PROCCESS_REQUEST,
    REVOKE_INVITE_ANNOUNCEMENT,
    ROLE_ADMIN,
    ROLE_GUEST,
    ROLE_LEADER,
    ROLE_MEMBER,
    ROLE_MOD,
    SAME_NAME_CHANGE,
    SAVED_DATABASE,
    SAVE_PROCCESS_REQUEST,
    SCHEMATIC_LEFT_SELECT,
    SCHEMATIC_NOT_READY,
    SCHEMATIC_PROCCESS_REQUEST,
    SCHEMATIC_READY_TO_CREATE,
    SCHEMATIC_RIGHT_SELECT,
    SCHEMATIC_SAVED,
    SELF_ROLE_CHANGE,
    SET_UPGRADE_LEVEL,
    SET_UPGRADE_LEVEL_NAME,
    SET_WARP,
    SET_WARP_OUTSIDE,
    SPY_TEAM_CHAT_FORMAT,
    TEAM_CHAT_FORMAT,
    TELEPORTED_SUCCESS,
    TELEPORTED_TO_WARP,
    TELEPORT_LOCAITON_OUTSIDE_ISLAND,
    TOGGLED_BYPASS_OFF,
    TOGGLED_BYPASS_ON,
    TOGGLED_FLY_OFF,
    TOGGLED_FLY_ON,
    TOGGLED_SCHEMATIC_OFF,
    TOGGLED_SCHEMATIC_ON,
    TOGGLED_SPY_OFF,
    TOGGLED_SPY_ON,
    TOGGLED_STACKED_BLCOKS_OFF,
    TOGGLED_STACKED_BLCOKS_ON,
    TOGGLED_TEAM_CHAT_OFF,
    TOGGLED_TEAM_CHAT_ON,
    TOGGLED_WORLD_BORDER_OFF,
    TOGGLED_WORLD_BORDER_ON,
    TRANSFER_ADMIN,
    TRANSFER_ADMIN_ALREADY_LEADER,
    TRANSFER_ADMIN_DIFFERENT_ISLAND,
    TRANSFER_ADMIN_NOT_LEADER,
    TRANSFER_ALREADY_LEADER,
    TRANSFER_BROADCAST,
    TRANSFER_NOT_A_MEMBER,
    UNBAN_ANNOUNCEMENT,
    UNKNOWN_COMMAND,
    UNSAFE_WARP,
    UPDATED_PERMISSION,
    WARP_ALREADY_EXIST,
    WITHDRAWN_MONEY,
    WITHDRAWN_MONEY_NAME,
    WITHDRAW_ALL_MONEY,
    WITHDRAW_ANNOUNCEMENT;

    private String message;

    public boolean isEmpty(){
        return message == null || message.isEmpty();
    }

    public String getMessage(Object... objects){
        if(!isEmpty()) {
            String msg = message;

            for (int i = 0; i < objects.length; i++)
                msg = msg.replace("{" + i + "}", objects[i].toString());

            return msg;
        }

        return null;
    }

    public void send(SuperiorPlayer superiorPlayer, Object... objects){
        send(superiorPlayer.asPlayer(), objects);
    }

    public void send(CommandSender sender, Object... objects){
        String message = getMessage(objects);
        if(message != null && sender != null)
            sender.sendMessage(message);
    }

    private void setMessage(String message){
        this.message = message;
    }

    private static SuperiorSkyblockPlugin plugin = SuperiorSkyblockPlugin.getPlugin();

    public static void reload(){
        SuperiorSkyblockPlugin.log("Loading messages started...");
        long startTime = System.currentTimeMillis();
        int messagesAmount = 0;
        File file = new File(plugin.getDataFolder(), "lang.yml");

        if(!file.exists())
            plugin.saveResource("lang.yml", false);

        CommentedConfiguration cfg = new CommentedConfiguration(LangComments.class, file);
        cfg.resetYamlFile(plugin, "lang.yml");

        for(Locale locale : values()){
            locale.setMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString(locale.name(), "")));
            messagesAmount++;
        }

        SuperiorSkyblockPlugin.log(" - Found " + messagesAmount + " messages in lang.yml.");
        SuperiorSkyblockPlugin.log("Loading messages done (Took " + (System.currentTimeMillis() - startTime) + "ms)");
    }

    public static void sendMessage(SuperiorPlayer superiorPlayer, String message){
        sendMessage(superiorPlayer.asPlayer(), message);
    }

    public static void sendMessage(CommandSender sender, String message){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    private static Set<UUID> noInteractMessages = new HashSet<>();

    public static void sendProtectionMessage(SuperiorPlayer superiorPlayer){
        sendProtectionMessage(superiorPlayer.asPlayer());
    }

    public static void sendProtectionMessage(Player player){
        if(!noInteractMessages.contains(player.getUniqueId())){
            noInteractMessages.add(player.getUniqueId());
            ISLAND_PROTECTED.send(player);
            Executor.sync(() -> noInteractMessages.remove(player.getUniqueId()), 60L);
        }
    }

}
