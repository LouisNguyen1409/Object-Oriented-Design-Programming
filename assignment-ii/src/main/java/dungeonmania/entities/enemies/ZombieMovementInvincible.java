
package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieMovementInvincible implements ZombieMovementStrategy {
    @Override
    public Position move(Game game, ZombieToast zombie) {

        Position nextPos;
        GameMap map = game.getMap();
        Position plrDiff = Position.calculatePositionBetween(map.getPlayer().getPosition(), zombie.getPosition());

        Position moveX = (plrDiff.getX() >= 0) ? Position.translateBy(zombie.getPosition(), Direction.RIGHT)
                : Position.translateBy(zombie.getPosition(), Direction.LEFT);
        Position moveY = (plrDiff.getY() >= 0) ? Position.translateBy(zombie.getPosition(), Direction.UP)
                : Position.translateBy(zombie.getPosition(), Direction.DOWN);
        Position offset = zombie.getPosition();
        if (plrDiff.getY() == 0 && map.canMoveTo(zombie, moveX))
            offset = moveX;
        else if (plrDiff.getX() == 0 && map.canMoveTo(zombie, moveY))
            offset = moveY;
        else if (Math.abs(plrDiff.getX()) >= Math.abs(plrDiff.getY())) {
            if (map.canMoveTo(zombie, moveX))
                offset = moveX;
            else if (map.canMoveTo(zombie, moveY))
                offset = moveY;
            else
                offset = zombie.getPosition();
        } else {
            if (map.canMoveTo(zombie, moveY))
                offset = moveY;
            else if (map.canMoveTo(zombie, moveX))
                offset = moveX;
            else
                offset = zombie.getPosition();
        }
        nextPos = offset;

        return nextPos;
    }

}
