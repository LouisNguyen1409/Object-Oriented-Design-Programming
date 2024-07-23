# Assignment II Pair Blog

## Task 1) Code Analysis and Refactoring ⛏️

### a) From DRY to Design Patterns

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/23T3/teams/M13A_JIGGLYPUFF/assignment-ii/-/merge_requests/4)

> i. Look inside src/main/java/dungeonmania/entities/enemies. Where can you notice an instance of repeated code? Note down the particular offending lines/methods/fields.

The code within enemies is pretty solid, the exception being the movement logic of the mercenary is a bit convoluted. The logic within the method makes it very bloated and confusing, particularly from line 86-132.

> ii. What Design Pattern could be used to improve the quality of the code and avoid repetition? Justify your choice by relating the scenario to the key characteristics of your chosen Design Pattern.

Using a strategy pattern seems to me to be the best option as it requires a query into the state of/ relationship to the player. A state pattern may work but I'm not sure how to handle potions using that method as it is player side rather than mercenary side.

> iii. Using your chosen Design Pattern, refactor the code to remove the repetition.

The code now uses a strategy pattern, that runs a different move method based on a set of conditions.

### b) Observer Pattern

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/23T3/teams/M13A_JIGGLYPUFF/assignment-ii/-/merge_requests/1)

> Identify one place where the Observer Pattern is present in the codebase, and outline how the implementation relates to the key characteristics of the Observer Pattern.

The Observer Pattern is present in file Bomb.java.

In this Observer Pattern, `Switch` represents the observer and `Bomb` represents the subject.

The `Bomb` class maintain a list of `Switch` objects in the `subs` field. The `subscribe` method allows to add a `Switch` object to `subs` list. The `notify` method allows the `Bomb` object to notify all subscribed `Switch` object when it explodes.

This implementation of the Observer Pattern demonstrates the key characteristics of loose coupling between the subject (`Bomb`) and observers (`Switch`), the ability for observers to subscribe and unsubscribe dynamically, and the ability for multiple observers to receive updates from the subject simultaneously.

### c) Inheritance Design

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/23T3/teams/M13A_JIGGLYPUFF/assignment-ii/-/merge_requests/5)

> i. Name the code smell present in the above code. Identify all subclasses of Entity which have similar code smells that point towards the same root cause.

These method override an abstract method stub within the entity class but don't actually have any functionallity. This is true for nearly every type of entity, including enemies and treasures.

> ii. Redesign the inheritance structure to solve the problem, in doing so remove the smells.

By making the methods in entity non abstract and having a default setting of returning nothing, I cleared the clutter on the subclasses that don't have a defined return/ logic for these methods as they now inherit them rather than override it.

### d) More Code Smells

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/23T3/teams/M13A_JIGGLYPUFF/assignment-ii/-/merge_requests/6)

> i. What design smell is present in the above description?

The system is clearly far to interconnected and relies on multiple classes to do there job. In the GameMap.java, the triggerOverlapEvent method input always ran the onOverlap method of the initial object in the area. This meant it always run the onOverlap for the collectable as they cannot move. 

> ii. Refactor the code to resolve the smell and underlying problem causing it.

I changed the triggerOverlapEvent method to check specifically for the Collectable entities. In this case it runs the players onOverlap method. The only time this doesn't occur is for the bomb which has specific onOverlap logic. This whole system could do with a refresh as it is super convoluted. It breaks for anything that isn't a collectable. Potentially something that could be fully refactored, not just collectables.

### e) Open-Closed Goals

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/23T3/teams/M13A_JIGGLYPUFF/assignment-ii/-/merge_requests/2)

> i. Do you think the design is of good quality here? Do you think it complies with the open-closed principle? Do you think the design should be changed?

- In my personal opinion, the design of code in goals package does not have good quality.

- It does not complies with the open-closed priciple.

- In my personal opinion, the design of code in goals package should be changed.


> ii. If you think the design is sufficient as it is, justify your decision. If you think the answer is no, pick a suitable Design Pattern that would improve the quality of the code and refactor the code accordingly.

- The design of code does not good since when adding new goal, it will violence the open-closed principle.

- In my personal opinion, I will apply `Composite Pattern` for `Goal` since `AND` and `OR` are the compound node.

- I will add the following files in goals package:
    - ExitGoal.java
    - BouldersGoal.java
    - TreasureGoal.java
    - AndGoal.java
    - OrGoal.java

- I will make `Goal` from class to interface with `achieved` method and `toString` method.

### f) Open Refactoring

[Merge Request 1](/put/links/here)

[Briefly explain what you did]

[Merge Request 2](/put/links/here)

[Briefly explain what you did]

Add all other changes you made in the same format here:

## Task 2) Evolution of Requirements 👽

### a) Microevolution - Enemy Goal

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/23T3/teams/M13A_JIGGLYPUFF/assignment-ii/-/merge_requests/7)

**Assumptions**

Nothing.

**Design**

- Adding new case in GoalFactory.java for enemies.
- Create new file name EnemiesGoal.java in goal package implements Goal interface.
- Create new file name GoalTest.java for testing new enemies goal.

**Changes after review**

[Design review/Changes made]

**Test list**

- Test with enemies goal and without spawners.
- Test with enemies goal and with spawners.

**Other notes**

Nothing.

### Sunstone and More Buildables

[Links to your merge requests](/put/links/here)

**Assumptions**

- Midnight armour can be spawned if a zombie is spawned on the same tick as its creation.

**Design**

- Initially I had the Sunstone as a Subclass of treasure but that caused issues with crafting and bribing so it currently is just a subclass of the Collectables. This means I had to slightly edit the treasure goal to account for the new requirements.

**Changes after review**

[Design review/Changes made]

**Test list**

- BuildSceptreOneSunstone
- BuildSceptreTwoSunstone
- BuildMidnightArmour
- BuildMidnightArmourZombie
- BuildShieldWithSunstone
- TreasureGoalWithSunstone
Any other functionallity can be tested within these tests
Such as opening a door with the sunstone and mind_control.

**Other notes**

[Any other notes]

### Choice 2 (f) Logic Switches (20 marks))

[Links to your merge requests](/put/links/here)

**Assumptions**

Nothing.

**Design**

[Design]

**Changes after review**

[Design review/Changes made]

**Test list**

[Test List]

**Other notes**

[Any other notes]

### Choice 3 (Insert choice) (If you have a 3rd member)

[Links to your merge requests](/put/links/here)

**Assumptions**

[Any assumptions made]

**Design**

- Adding interface `Conductor` for `Switch` object and `Wire` object.
- Adding `Wire.java` implements `Conductor` interface to handle Wire logic.
- Editing `Swtich.java`:
    - Adding `activeTick` field to track the tick that the entity is activated.
    - Adding `subcribe` and `unsubscribe` for `LogicalEntities` and `Conductor`.
    - Adding condition to `activate` and `inactivate` for `LogicalEntities` and `Conductor`.
- Adding interface `LogicalObserver` for `LogicalEntities` object.
- Editing `EntityFactory.java`:
    - Adding new case for `light_bulb_off`, `switch_door` and `wire`.
    - Edit `Bomb` case due to add logical condition.
- Adding new package for Logical entities.
- Adding `LogicalEntities.java` implements `LogicalObserver` interface to handle Logical entities logic.
- Adding `SwitchDoor.java` extends `LogicalEntities.java` class to handle activate, inactivate and canMoveOnto logic.
- Adding `LightBulb.java` extends `LogicalEntities.java` class to handle activate and inactivate logic.
- Editing `Bomb.java`:
    - Adding new constructor for Logical Bomb.
    - Editing `notify` method to decide whether the bomb is Logical Bomb or Normal Bomb to explode.
- Adding new package for Logical condition
- Adding `LogicalCondition.java` and making this becomes abstract class.
- Adding `And.java` to handle AND logic.
- Adding `Or.java` to handle OR logic.
- Adding `CoAnd.java` to handle CO_AND logic.
- Adding `Xor.java` to handle XOR logic.
- Adding `ConditionFactory.java` which will implments Factory Method Pattern for making new logical object.
- Editing `default.json`: Adding new skin for switch door and light bulb
- Editing `GameMap.java`: Addding function linking wire, switch, light bulb, bombs and switch door.
- Editing `GraphNodeFactory.java`: Adding new lightbulb and switch door case.


**Changes after review**

[Design review/Changes made]

**Test list**

- Testing And Condition.
- Testing Or Condition.
- Testing Xor Condition.
- Testing CoAnd Condition.
- Testing Bomb with logic.
- Testing Bomb without logic.
- Testing Wire Conductor.
- Testing SwitchDoor: close -> open.
- Testing SwitchDoor: close -> open -> close.
- Testing LightBulb: off -> on.
- Testing LightBulb: off -> on -> off.

**Other notes**

Nothing

## Task 3) Investigation Task ⁉️

[Merge Request 1](https://nw-syd-gitlab.cseunsw.tech/COMP2511/23T3/teams/M13A_JIGGLYPUFF/assignment-ii/-/merge_requests/8)

- I change the logic idea `interact` function for Destroy Zombie Toast Spawner in `ZombieToastSpawner.java`.
- I change the `interact` function so that the ZombieToastSpawner deletes from the map when destroy.
- I change the logic test for `toastDestruction` test case in `ZombieTest.java`.
- I change the logic test for `enemiesGoalWithSpawner` test case in `GoalTest.java`.

[Merge Request 2](https://nw-syd-gitlab.cseunsw.tech/COMP2511/23T3/teams/M13A_JIGGLYPUFF/assignment-ii/-/merge_requests/11)

- I change the logic idea for `pickUp` function for pick up the Entity with type Key in `Player.java`.
- I change the `pickUp` function so that the player can carry only one key at a time.
- I change the logic test for `cannotPickupTwoKeys` test case in `DoorsKeysTest.java`.


Add all other changes you made in the same format here:
