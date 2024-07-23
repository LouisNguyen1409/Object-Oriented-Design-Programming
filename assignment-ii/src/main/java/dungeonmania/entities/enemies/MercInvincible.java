package dungeonmania.entities.enemies;

import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercInvincible implements MercMovementStrategy {
    @Override
    public Position move(Position nextPos, GameMap map, Player player, Mercenary mercenary) {
        Position plrDiff = Position.calculatePositionBetween(map.getPlayer().getPosition(), mercenary.getPosition());

        Position moveX = (plrDiff.getX() >= 0) ? Position.translateBy(mercenary.getPosition(), Direction.RIGHT)
                : Position.translateBy(mercenary.getPosition(), Direction.LEFT);
        Position moveY = (plrDiff.getY() >= 0) ? Position.translateBy(mercenary.getPosition(), Direction.UP)
                : Position.translateBy(mercenary.getPosition(), Direction.DOWN);
        Position offset = mercenary.getPosition();
        if (plrDiff.getY() == 0 && map.canMoveTo(mercenary, moveX))
            offset = moveX;
        else if (plrDiff.getX() == 0 && map.canMoveTo(mercenary, moveY))
            offset = moveY;
        else if (Math.abs(plrDiff.getX()) >= Math.abs(plrDiff.getY())) {
            if (map.canMoveTo(mercenary, moveX))
                offset = moveX;
            else if (map.canMoveTo(mercenary, moveY))
                offset = moveY;
            else
                offset = mercenary.getPosition();
        } else {
            if (map.canMoveTo(mercenary, moveY))
                offset = moveY;
            else if (map.canMoveTo(mercenary, moveX))
                offset = moveX;
            else
                offset = mercenary.getPosition();
        }
        return offset;
    }
}
