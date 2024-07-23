package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    public static final double DEFAULT_HEALTH = 5.0;
    public static final double DEFAULT_ATTACK = 6.0;

    public ZombieToast(Position position, double health, double attack) {
        super(position, health, attack);
        Game.activeZombies += 1;
    }

    @Override
    public void move(Game game) {
        Position nextPos;
        GameMap map = game.getMap();
        if (map.getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
            ZombieMovementInvincible movementInvincible = new ZombieMovementInvincible();
            nextPos = movementInvincible.move(game, this);
        } else {
            ZombieMovementRegular movementRegular = new ZombieMovementRegular();
            nextPos = movementRegular.move(game, this);
        }
        game.getMap().moveTo(this, nextPos);

    }

    @Override
    public void onDestroy(GameMap map) {
        Game g = map.getGame();
        g.unsubscribe(getId());
        Game.activeZombies -= 1;
    }

}
