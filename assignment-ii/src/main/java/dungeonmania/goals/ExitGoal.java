package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.util.Position;
import dungeonmania.entities.Exit;
import java.util.List;
import dungeonmania.entities.Entity;

public class ExitGoal implements Goal {
    @Override
    public boolean achieved(Game game) {
        if (game.getPlayer() == null)
            return false;

        Player character = game.getPlayer();
        Position pos = character.getPosition();
        List<Exit> es = game.getMap().getEntities(Exit.class);
        if (es == null || es.size() == 0)
            return false;
        return es.stream().map(Entity::getPosition).anyMatch(pos::equals);
    }

    @Override
    public String toString(Game game) {
        return achieved(game) ? "" : ":exit";
    }
}
