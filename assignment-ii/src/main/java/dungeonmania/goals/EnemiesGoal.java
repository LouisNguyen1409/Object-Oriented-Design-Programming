package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.enemies.ZombieToastSpawner;

public class EnemiesGoal implements Goal {
    private int target;

    public EnemiesGoal(int target) {
        this.target = target;

    }

    @Override
    public boolean achieved(Game game) {
        return game.getPlayer() == null ? false
                : (game.getBattles() >= target && game.getMap().getEntities(ZombieToastSpawner.class).size() == 0);
    }

    @Override
    public String toString(Game game) {
        return achieved(game) ? "" : ":enemies";
    }

}
