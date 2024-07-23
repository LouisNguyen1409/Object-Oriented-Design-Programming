package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.util.Position;

public interface ZombieMovementStrategy {
    public Position move(Game game, ZombieToast zombie);
}
