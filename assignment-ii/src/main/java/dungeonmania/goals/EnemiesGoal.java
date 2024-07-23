package dungeonmania.goals;

import dungeonmania.Game;

public class EnemiesGoal implements Goal {
    private int target;

    public EnemiesGoal(int target) {
        this.target = target;
    }

    @Override
    public boolean achieved(Game game) {
        return game.getPlayer() == null ? false : game.getCollectedTreasureCount() >= target;
    }

    @Override
    public String toString(Game game) {
        return achieved(game) ? "" : ":treasure";
    }

}
