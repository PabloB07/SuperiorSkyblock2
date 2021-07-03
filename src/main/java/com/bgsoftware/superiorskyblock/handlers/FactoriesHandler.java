package com.bgsoftware.superiorskyblock.handlers;

import com.bgsoftware.superiorskyblock.api.data.DatabaseBridge;
import com.bgsoftware.superiorskyblock.api.factory.BanksFactory;
import com.bgsoftware.superiorskyblock.api.factory.DatabaseBridgeFactory;
import com.bgsoftware.superiorskyblock.api.factory.IslandsFactory;
import com.bgsoftware.superiorskyblock.api.factory.PlayersFactory;
import com.bgsoftware.superiorskyblock.api.handlers.FactoriesManager;
import com.bgsoftware.superiorskyblock.api.handlers.GridManager;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.island.bank.IslandBank;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.bgsoftware.superiorskyblock.data.DatabaseResult;
import com.bgsoftware.superiorskyblock.data.sql.SQLDatabaseBridge;
import com.bgsoftware.superiorskyblock.island.SIsland;
import com.bgsoftware.superiorskyblock.island.bank.SIslandBank;
import com.bgsoftware.superiorskyblock.player.SSuperiorPlayer;
import com.google.common.base.Preconditions;
import org.bukkit.Location;

import java.util.UUID;

public final class FactoriesHandler implements FactoriesManager {

    private IslandsFactory islandsFactory;
    private PlayersFactory playersFactory;
    private BanksFactory banksFactory;
    private DatabaseBridgeFactory databaseBridgeFactory;

    @Override
    public void registerIslandsFactory(IslandsFactory islandsFactory) {
        Preconditions.checkNotNull(islandsFactory, "islandsFactory parameter cannot be null.");
        this.islandsFactory = islandsFactory;
    }

    @Override
    public void registerPlayersFactory(PlayersFactory playersFactory) {
        Preconditions.checkNotNull(playersFactory, "playersFactory parameter cannot be null.");
        this.playersFactory = playersFactory;
    }

    @Override
    public void registerBanksFactory(BanksFactory banksFactory) {
        Preconditions.checkNotNull(banksFactory, "banksFactory parameter cannot be null.");
        this.banksFactory = banksFactory;
    }

    @Override
    public void registerDatabaseBridgeFactory(DatabaseBridgeFactory databaseBridgeFactory) {
        Preconditions.checkNotNull(databaseBridgeFactory, "databaseBridgeFactory parameter cannot be null.");
        this.databaseBridgeFactory = databaseBridgeFactory;
    }

    public Island createIsland(GridHandler grid, DatabaseResult resultSet) {
        SIsland island = new SIsland(grid, resultSet);
        return islandsFactory == null ? island : islandsFactory.createIsland(island);
    }

    public Island createIsland(SuperiorPlayer superiorPlayer, UUID uuid, Location location, String islandName, String schemName){
        SIsland island = new SIsland(superiorPlayer, uuid, location, islandName, schemName);
        return islandsFactory == null ? island : islandsFactory.createIsland(island);
    }

    public SuperiorPlayer createPlayer(DatabaseResult resultSet) {
        SSuperiorPlayer superiorPlayer = new SSuperiorPlayer(resultSet);
        return playersFactory == null ? superiorPlayer : playersFactory.createPlayer(superiorPlayer);
    }

    public SuperiorPlayer createPlayer(UUID player) {
        SSuperiorPlayer superiorPlayer = new SSuperiorPlayer(player);
        return playersFactory == null ? superiorPlayer : playersFactory.createPlayer(superiorPlayer);
    }

    public IslandBank createIslandBank(Island island){
        SIslandBank islandBank = new SIslandBank(island);
        return banksFactory == null ? islandBank : banksFactory.createIslandBank(island, islandBank);
    }

    public boolean hasCustomDatabaseBridge(){
        return databaseBridgeFactory != null;
    }

    public DatabaseBridge createDatabaseBridge(Island island){
        SQLDatabaseBridge databaseBridge = island == null ? new SQLDatabaseBridge(null, null) :
                new SQLDatabaseBridge(island::getUniqueId, "island");
        return databaseBridgeFactory == null ? databaseBridge :
                databaseBridgeFactory.createIslandsDatabaseBridge(island, databaseBridge);
    }

    public DatabaseBridge createDatabaseBridge(SuperiorPlayer superiorPlayer){
        SQLDatabaseBridge databaseBridge = superiorPlayer == null ?
                new SQLDatabaseBridge(null, null) :
                new SQLDatabaseBridge(superiorPlayer::getUniqueId, "player");
        return databaseBridgeFactory == null ? databaseBridge :
                databaseBridgeFactory.createPlayersDatabaseBridge(superiorPlayer, databaseBridge);
    }

    public DatabaseBridge createDatabaseBridge(GridManager gridManager){
        SQLDatabaseBridge databaseBridge = new SQLDatabaseBridge(null, null);
        return databaseBridgeFactory == null ? databaseBridge :
                databaseBridgeFactory.createGridDatabaseBridge(gridManager, databaseBridge);
    }

}
