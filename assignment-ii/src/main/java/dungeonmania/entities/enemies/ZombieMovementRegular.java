package dungeonmania.entities.enemies;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ZombieMovementRegular implements ZombieMovementStrategy {
    private Random randGen = new Random();

    @Override
    public Position move(Game game, ZombieToast zombie) {

        Position nextPos;
        GameMap map = game.getMap();

        List<Position> pos = zombie.getPosition().getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> map.canMoveTo(zombie, p)).collect(Collectors.toList());
        if (pos.size() == 0) {
            nextPos = zombie.getPosition();
        } else {
            nextPos = pos.get(randGen.nextInt(pos.size()));
        }
        return nextPos;
    }

}
