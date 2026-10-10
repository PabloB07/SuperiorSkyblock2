package com.bgsoftware.superiorskyblock.spawnmenu;

import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.bgsoftware.superiorskyblock.core.messages.Message;
import com.bgsoftware.superiorskyblock.core.threads.BukkitExecutor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class SpawnMenuListener implements Listener {

    private final SuperiorSkyblockPlugin plugin;
    private final Set<UUID> holdingItem = new HashSet<>();
    private final Map<UUID, Long> lastInteractionTimestamps = new HashMap<>();

    public SpawnMenuListener(SuperiorSkyblockPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        BukkitExecutor.sync(() -> {
            if (!player.isOnline())
                return;
            giveItem(player);
            checkLocation(player, player.getLocation());
        }, 5L);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        holdingItem.remove(e.getPlayer().getUniqueId());
        lastInteractionTimestamps.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom().getBlockX() == e.getTo().getBlockX() &&
                e.getFrom().getBlockY() == e.getTo().getBlockY() &&
                e.getFrom().getBlockZ() == e.getTo().getBlockZ())
            return;
        checkLocation(e.getPlayer(), e.getTo());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent e) {
        if (e.getTo() != null)
            checkLocation(e.getPlayer(), e.getTo());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if (!plugin.getSpawnMenu().isSpawnItem(e.getItem()))
            return;

        e.setCancelled(true);

        // On 1.9+ the event is called for both hands; only handle the first one.
        long now = System.currentTimeMillis();
        Long lastInteract = lastInteractionTimestamps.get(e.getPlayer().getUniqueId());
        if (lastInteract != null && now - lastInteract < 100L)
            return;
        lastInteractionTimestamps.put(e.getPlayer().getUniqueId(), now);

        if (isInSpawn(e.getPlayer().getLocation())) {
            plugin.getSpawnMenu().open(e.getPlayer());
        } else {
            Message.SPAWN_ONLY_AT_SPAWN.send(e.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(PlayerDropItemEvent e) {
        if (plugin.getSpawnMenu().isSpawnItem(e.getItemDrop().getItemStack()))
            e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory top = e.getView().getTopInventory();
        if (!(top.getHolder() instanceof SpawnMenu.SpawnMenuHolder))
            return;

        e.setCancelled(true);

        if (e.getRawSlot() >= 0 && e.getRawSlot() < SpawnMenu.SIZE && e.getWhoClicked() instanceof Player)
            plugin.getSpawnMenu().handleClick((Player) e.getWhoClicked(), e.getRawSlot());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getView().getTopInventory().getHolder() instanceof SpawnMenu.SpawnMenuHolder)
            e.setCancelled(true);
    }

    private void checkLocation(Player player, Location location) {
        boolean inSpawn = isInSpawn(location);
        boolean holding = holdingItem.contains(player.getUniqueId());

        if (inSpawn && !holding) {
            giveItem(player);
        } else if (!inSpawn && holding) {
            removeItem(player);
        }
    }

    private void giveItem(Player player) {
        removeItem(player);

        SuperiorPlayer superiorPlayer = plugin.getPlayers().getSuperiorPlayer(player);
        ItemStack item = plugin.getSpawnMenu().createItem(superiorPlayer.getUserLocale());
        player.getInventory().addItem(item);
        holdingItem.add(player.getUniqueId());
    }

    private void removeItem(Player player) {
        holdingItem.remove(player.getUniqueId());
        ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            if (plugin.getSpawnMenu().isSpawnItem(contents[i]))
                player.getInventory().setItem(i, null);
        }
    }

    private boolean isInSpawn(Location location) {
        Island island = plugin.getGrid().getIslandAt(location);
        return island != null && island.isSpawn();
    }

}
