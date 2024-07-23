package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class Sceptre extends Buildable {
    private int mindControlDuration;

    public Sceptre(int duration) {
        super(null);
        this.mindControlDuration = duration;
    }

    @Override
    public void use(Game game) {

    }

    @Override
    public int getDurability() {
        return 0;
    }

    public int getMindControlDuration() {
        return this.mindControlDuration;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 0, 0));
    }

}
