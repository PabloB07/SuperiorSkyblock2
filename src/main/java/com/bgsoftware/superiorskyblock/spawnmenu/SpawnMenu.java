package com.bgsoftware.superiorskyblock.spawnmenu;

import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.island.PlayerRole;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.bgsoftware.superiorskyblock.core.EnumHelper;
import com.bgsoftware.superiorskyblock.core.formatting.Formatters;
import com.bgsoftware.superiorskyblock.core.logging.Log;
import com.bgsoftware.superiorskyblock.core.messages.Message;
import com.bgsoftware.superiorskyblock.island.IslandUtils;
import com.bgsoftware.superiorskyblock.island.top.SortingTypes;
import com.bgsoftware.superiorskyblock.player.PlayerLocales;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SpawnMenu {

    static final int SIZE = 27;
    static final int SLOT_TOP = 11;
    static final int SLOT_PROFILE = 13;
    static final int SLOT_CREATE = 15;
    static final int SLOT_LANGUAGE = 22;

    // Menu files whose bundled Spanish translations get applied when the language flag is clicked.
    private static final String[] SPANISH_MENUS = {
            "bank-logs.yml", "banned-players.yml", "biomes.yml", "border-color.yml", "confirm-ban.yml",
            "confirm-disband.yml", "confirm-kick.yml", "confirm-leave.yml", "confirm-transfer.yml",
            "control-panel.yml", "coops.yml", "counts.yml", "global-warps.yml", "island-bank.yml",
            "island-chest.yml", "island-creation.yml", "island-rate.yml", "island-ratings.yml",
            "member-manage.yml", "member-role.yml", "members.yml", "missions.yml", "missions-category.yml",
            "permissions.yml", "player-language.yml", "settings.yml", "top-islands.yml", "unique-visitors.yml",
            "upgrades.yml", "values.yml", "visitors.yml", "warp-categories.yml", "warp-category-icon-edit.yml",
            "warp-category-manage.yml", "warp-icon-edit.yml", "warp-manage.yml", "warps.yml"
    };

    // The main sourceSet compiles against the 1.8 API, where ItemMeta doesn't
    // have PersistentDataContainers. The getter is resolved through reflection instead.
    private static final Method META_PDC;

    static {
        Method metaPdc;
        try {
            metaPdc = Class.forName("org.bukkit.inventory.meta.ItemMeta").getMethod("getPersistentDataContainer");
        } catch (ReflectiveOperationException error) {
            metaPdc = null;
        }
        META_PDC = metaPdc;
    }

    private final SuperiorSkyblockPlugin plugin;
    private final NamespacedKey itemKey;

    public SpawnMenu(SuperiorSkyblockPlugin plugin) {
        this.plugin = plugin;
        this.itemKey = new NamespacedKey(plugin, "spawn_menu_item");
    }

    public ItemStack createItem(Locale locale) {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(text(Message.SPAWN_ITEM_NAME.getMessage(locale)));
        meta.setLore(lore(Message.SPAWN_ITEM_LORE.getMessage(locale)));
        markItem(meta);
        item.setItemMeta(meta);
        return item;
    }

    public boolean isSpawnItem(ItemStack item) {
        if (item == null || item.getType() != Material.NETHER_STAR || !item.hasItemMeta())
            return false;

        Object pdc = getPersistentDataContainer(item.getItemMeta());

        return pdc != null && ((PersistentDataContainer) pdc).has(itemKey, PersistentDataType.BYTE);
    }

    public void open(Player player) {
        SuperiorPlayer superiorPlayer = plugin.getPlayers().getSuperiorPlayer(player);
        Locale locale = superiorPlayer.getUserLocale();

        SpawnMenuHolder holder = new SpawnMenuHolder();
        Inventory inventory = Bukkit.createInventory(holder, SIZE, text(Message.SPAWN_MENU_TITLE.getMessage(locale)));
        holder.inventory = inventory;

        inventory.setItem(SLOT_TOP, named(material("DIAMOND"),
                Message.SPAWN_MENU_TOP_NAME.getMessage(locale), Message.SPAWN_MENU_TOP_LORE.getMessage(locale)));
        inventory.setItem(SLOT_PROFILE, named(material("BOOK"),
                Message.SPAWN_MENU_PROFILE_NAME.getMessage(locale), profileLore(superiorPlayer, locale)));
        inventory.setItem(SLOT_CREATE, named(material("GRASS_BLOCK", "GRASS"),
                Message.SPAWN_MENU_CREATE_NAME.getMessage(locale), Message.SPAWN_MENU_CREATE_LORE.getMessage(locale)));
        inventory.setItem(SLOT_LANGUAGE, chileanBanner(Message.SPAWN_MENU_LANGUAGE_NAME.getMessage(locale),
                Message.SPAWN_MENU_LANGUAGE_LORE.getMessage(locale)));

        player.openInventory(inventory);
    }

    public void handleClick(Player player, int slot) {
        SuperiorPlayer superiorPlayer = plugin.getPlayers().getSuperiorPlayer(player);

        switch (slot) {
            case SLOT_TOP:
                player.closeInventory();
                plugin.getMenus().openTopIslands(superiorPlayer, null, SortingTypes.getIslandTopSorting());
                break;
            case SLOT_CREATE:
                if (superiorPlayer.getIsland() != null) {
                    Message.SPAWN_MENU_CREATE_HAS_ISLAND.send(superiorPlayer);
                    break;
                }
                player.closeInventory();
                plugin.getMenus().openIslandCreation(superiorPlayer, null, superiorPlayer.getName());
                break;
            case SLOT_LANGUAGE:
                superiorPlayer.setUserLocale(PlayerLocales.getLocale("es-ES"));
                player.closeInventory();
                translateMenusToSpanish();
                Message.SPAWN_LANGUAGE_CHANGED.send(superiorPlayer);
                break;
            default:
                break;
        }
    }

    private List<String> profileLore(SuperiorPlayer superiorPlayer, Locale locale) {
        List<String> lore = new ArrayList<>();
        Island island = superiorPlayer.getIsland();

        if (island == null) {
            lore.add(text(Message.SPAWN_MENU_PROFILE_NO_ISLAND.getMessage(locale)));
            return lore;
        }

        PlayerRole role = IslandUtils.getPlayerRole(island, superiorPlayer);
        String members = island.getIslandMembers(true).stream()
                .map(SuperiorPlayer::getName)
                .collect(Collectors.joining(", "));

        lore.add(text(Message.SPAWN_MENU_PROFILE_ISLAND.getMessage(locale) + island.getName()));
        lore.add(text(Message.SPAWN_MENU_PROFILE_ROLE.getMessage(locale) + role.getDisplayName()));
        lore.add(text(Message.SPAWN_MENU_PROFILE_LEVEL.getMessage(locale) + format(island.getIslandLevel())));
        lore.add(text(Message.SPAWN_MENU_PROFILE_WORTH.getMessage(locale) + format(island.getWorth())));
        lore.add(text(Message.SPAWN_MENU_PROFILE_MEMBERS.getMessage(locale) + members));
        return lore;
    }

    private void translateMenusToSpanish() {
        File menusFolder = new File(plugin.getDataFolder(), "menus");
        //noinspection ResultOfMethodCallIgnored
        menusFolder.mkdirs();

        int translated = 0;

        for (String fileName : SPANISH_MENUS) {
            try (InputStream resource = plugin.getResource("menus/es-ES/" + fileName)) {
                if (resource == null)
                    continue;

                Files.copy(resource, new File(menusFolder, fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
                translated++;
            } catch (Exception error) {
                Log.error(error, "An unexpected error occurred while translating menu ", fileName, ":");
            }
        }

        if (translated > 0) {
            Log.info("Translated " + translated + " menu files to Spanish.");
            plugin.getMenus().loadData();
        }
    }

    private void markItem(ItemMeta meta) {
        Object pdc = getPersistentDataContainer(meta);
        if (pdc != null)
            ((PersistentDataContainer) pdc).set(itemKey, PersistentDataType.BYTE, (byte) 1);
    }

    private static Object getPersistentDataContainer(ItemMeta meta) {
        if (META_PDC == null)
            return null;

        try {
            return META_PDC.invoke(meta);
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private static ItemStack chileanBanner(String name, String lore) {
        ItemStack banner = new ItemStack(material("WHITE_BANNER", "BANNER"));
        BannerMeta meta = (BannerMeta) banner.getItemMeta();

        // EnumHelper handles both legacy enum names and modern interface constants.
        PatternType bottomStripe = EnumHelper.getEnum(PatternType.class, "STRIPE_BOTTOM", "HALF_HORIZONTAL_BOTTOM");
        PatternType topLeftSquare = EnumHelper.getEnum(PatternType.class, "SQUARE_TOP_LEFT");
        if (bottomStripe != null)
            meta.addPattern(new Pattern(DyeColor.RED, bottomStripe));
        if (topLeftSquare != null)
            meta.addPattern(new Pattern(DyeColor.BLUE, topLeftSquare));

        meta.setDisplayName(text(name));
        meta.setLore(lore(lore));
        banner.setItemMeta(meta);
        return banner;
    }

    private static ItemStack named(Material material, String name, String... lore) {
        return named(material, name, Arrays.asList(lore));
    }

    private static ItemStack named(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(text(name));
        meta.setLore(lore.stream().map(SpawnMenu::text).collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    private static Material material(String... names) {
        return EnumHelper.getEnum(Material.class, names);
    }

    private static String text(String message) {
        return Formatters.COLOR_FORMATTER.format(message);
    }

    private static List<String> lore(String line) {
        List<String> lore = new ArrayList<>();
        lore.add(text(line));
        return lore;
    }

    private static String format(BigDecimal value) {
        return value.stripTrailingZeros().toPlainString();
    }

    static final class SpawnMenuHolder implements InventoryHolder {

        private Inventory inventory;

        @Override
        public Inventory getInventory() {
            return inventory;
        }

    }

}
