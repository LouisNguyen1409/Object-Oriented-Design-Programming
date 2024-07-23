package dungeonmania.entities;

import dungeonmania.map.GameMap;

import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.util.Position;

public class Door extends Entity {
    private boolean open = false;
    private int number;

    public Door(Position position, int number) {
        super(position.asLayer(Entity.DOOR_LAYER));
        this.number = number;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (open || entity instanceof Spider) {
            return true;
        }
        return (entity instanceof Player && (hasKey((Player) entity) || hasSunstone((Player) entity)));
    }

    @Override
    public boolean onOverlap(GameMap map, Entity entity) {
        if (!(entity instanceof Player)) {
            return false;
        }
        Player player = (Player) entity;

        if (hasSunstone(player)) {
            open();
            return true;
        }
        Inventory inventory = player.getInventory();
        Key key = inventory.getFirst(Key.class);

        if (hasKey(player)) {
            inventory.remove(key);
            open();
            return true;
        }
        return false;
    }

    private boolean hasKey(Player player) {
        Inventory inventory = player.getInventory();
        Key key = inventory.getFirst(Key.class);

        return (key != null && key.getnumber() == number);
    }

    private boolean hasSunstone(Player player) {
        Inventory inventory = player.getInventory();
        SunStone sunstone = inventory.getFirst(SunStone.class);

        return (sunstone != null);
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
    }
}
