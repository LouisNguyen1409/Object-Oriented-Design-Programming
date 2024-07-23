package dungeonmania.entities.enemies;

import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public interface MercMovementStrategy {
    public Position move(Position nextPos, GameMap map, Player player, Mercenary mercenary);
}
