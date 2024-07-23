package dungeonmania.mvp.task2;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BuildablesExtended {
    @Test
    @Tag("2D-1")
    @DisplayName("Test building a Sceptre")
    public void buildSceptreOneSunstone() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest1", "c_task2BuildablesTest");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);

        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

    }

    @Test
    @Tag("2D-2")
    @DisplayName("Test building a Sceptre and mind_control")
    public void buildSceptreTwoSunstone() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptreTest2", "c_task2BuildablesTest");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);

        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
    }

    @Test
    @Tag("2D-3")
    @DisplayName("Test building midnight_armour")
    public void buildMidnightArmour() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_buildMidnightArmour", "c_task2BuildablesTest");

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

    }

    @Test
    @Tag("2D-4")
    @DisplayName("Test building midnight_armour with a zombie")
    public void buildMidnightArmourZombie() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_buildMidnightArmourZombie", "c_task2BuildablesTest");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

    }

    @Test
    @Tag("2D-5")
    @DisplayName("Test building a shield with sunstone")
    public void buildShieldSunstone() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_buildShieldSunstone", "c_task2BuildablesTest");

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(0, TestUtils.getInventory(res, "shield").size());
        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(1, TestUtils.getInventory(res, "shield").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);

        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "shield").size());
        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(2, TestUtils.getInventory(res, "shield").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

    }

    @Test
    @Tag("2D-6")
    @DisplayName("Treasure Goal with Sunstone")
    public void treasureGoalSunstone() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_basicGoalsTest_sunstone", "c_task2BuildablesTest");

        res = dmc.tick(Direction.UP);
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        res = dmc.tick(Direction.UP);
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        res = dmc.tick(Direction.UP);
        assertEquals(3, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals("", TestUtils.getGoals(res));

    }
}
