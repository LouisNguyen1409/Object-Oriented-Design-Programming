package dungeonmania.mvp.task2;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class GoalTest {
    @Test
    @Tag("2A-1")
    @DisplayName("Test achieving a basic enemies goal, without spawner")
    public void enemiesGoal() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_goalTest_enemiesGoal", "c_goalTest_enemiesGoal");

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        res = controller.tick(Direction.RIGHT);
        List<EntityResponse> entities = res.getEntities();
        int spiderCount = TestUtils.countEntityOfType(entities, "spider");
        int zombieCount = TestUtils.countEntityOfType(entities, "zombie_toast");
        int mercCount = TestUtils.countEntityOfType(entities, "mercenary");
        assertEquals(1, spiderCount);
        assertEquals(0, zombieCount);
        assertEquals(1, mercCount);

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        res = controller.tick(Direction.RIGHT);
        entities = res.getEntities();
        spiderCount = TestUtils.countEntityOfType(entities, "spider");
        zombieCount = TestUtils.countEntityOfType(entities, "zombie_toast");
        mercCount = TestUtils.countEntityOfType(entities, "mercenary");
        assertEquals(1, spiderCount);
        assertEquals(0, zombieCount);
        assertEquals(0, mercCount);

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);
        entities = res.getEntities();
        spiderCount = TestUtils.countEntityOfType(entities, "spider");
        zombieCount = TestUtils.countEntityOfType(entities, "zombie_toast");
        mercCount = TestUtils.countEntityOfType(entities, "mercenary");
        assertEquals(0, spiderCount);
        assertEquals(0, zombieCount);
        assertEquals(0, mercCount);
        assertEquals(1, TestUtils.countEntityOfType(entities, "player"));

        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("2A-2")
    @DisplayName("Test achieving a basic enemies goal, with spawner")
    public void enemiesGoalWithSpawner() {
        //  W   W   W   W   W   W
        //  P   SW      M   S   W
        //  W   W   W   W   W   W
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res = controller.newGame("d_goalTest_enemiesGoalWithSpawner",
                "c_goalTest_enemiesGoalWithSpawner");
        assertEquals(1, TestUtils.getEntities(res, "zombie_toast_spawner").size());
        String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();
        List<EntityResponse> entities = res.getEntities();
        int spiderCount = TestUtils.countEntityOfType(entities, "spider");
        int zombieCount = TestUtils.countEntityOfType(entities, "zombie_toast");
        int mercCount = TestUtils.countEntityOfType(entities, "mercenary");
        assertEquals(0, spiderCount);
        assertEquals(0, zombieCount);
        assertEquals(1, mercCount);
        assertEquals(0, TestUtils.getInventory(res, "sword").size());

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        res = controller.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        assertThrows(InvalidActionException.class, () -> controller.interact(spawnerId));
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);

        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        assertThrows(IllegalArgumentException.class, () -> controller.interact("random_invalid_id"));

        res = assertDoesNotThrow(() -> controller.interact(spawnerId));

        assertEquals(0, TestUtils.countType(res, "zombie_toast_spawner"));

        entities = res.getEntities();
        spiderCount = TestUtils.countEntityOfType(entities, "spider");
        zombieCount = TestUtils.countEntityOfType(entities, "zombie_toast");
        mercCount = TestUtils.countEntityOfType(entities, "mercenary");
        assertEquals(0, spiderCount);
        assertEquals(0, zombieCount);
        assertEquals(0, mercCount);

        // System.out.println(TestUtils.getEntities(res, "zombie_toast_spawner").size());
        // assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertEquals("", TestUtils.getGoals(res));
    }
}
