package dungeonmania.entities.enemies;

import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class MercAlly implements MercMovementStrategy {
    @Override
    public Position move(Position nextPos, GameMap map, Player player, Mercenary mercenary) {
        nextPos = mercenary.isAdjacentToPlayer() ? player.getPreviousDistinctPosition()
                : map.dijkstraPathFind(mercenary.getPosition(), player.getPosition(), mercenary);
        if (!mercenary.isAdjacentToPlayer() && Position.isAdjacent(player.getPosition(), nextPos))
            mercenary.setAdjacent(true);
        return nextPos;
    }
}
