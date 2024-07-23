package dungeonmania.entities.enemies;

import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class MercRegular implements MercMovementStrategy {
    @Override
    public Position move(Position nextPos, GameMap map, Player player, Mercenary mercenary) {
        return map.dijkstraPathFind(mercenary.getPosition(), player.getPosition(), mercenary);
    }
}
