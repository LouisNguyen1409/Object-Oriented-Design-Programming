
package dungeonmania.entities.collectables;

import dungeonmania.entities.Entity;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.util.Position;

public class Collectable extends Entity implements InventoryItem {
    // This subclass of entity exists to help link collectables into a single subclass
    // Functionally it doesn't do (anything) but it allows the collection to be on the player side
    public Collectable(Position position) {
        super(position);
    }
}
