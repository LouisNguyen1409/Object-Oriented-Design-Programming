package dungeonmania.entities.enemies;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class MercInvisible implements MercMovementStrategy {
    @Override
    public Position move(Position nextPos, GameMap map, Player player, Mercenary mercenary) {
        Random randGen = new Random();
        List<Position> pos = mercenary.getPosition().getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> map.canMoveTo(mercenary, p)).collect(Collectors.toList());
        if (pos.size() == 0) {
            nextPos = mercenary.getPosition();
            map.moveTo(mercenary, nextPos);
        } else {
            nextPos = pos.get(randGen.nextInt(pos.size()));
            map.moveTo(mercenary, nextPos);
        }
        return nextPos;
    }
}
