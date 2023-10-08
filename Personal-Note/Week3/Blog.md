There are 7 Designs Smells until week 3:
- Rigidity: Difficult to change. 1 change will cause others change.
- Fragility: Single change is made then the software will break in many places.

- Immobility: Hard to reuse.
- Viscosity: Easy to implement bad way and hard to implement in correct way.
- Opacity: Difficult to understand.

- Needless complexity: Not currently usefull. Develop ahead of requirement.
- Needless repetition: Repetitive code.

Characteristic of Good Design:
**Loose Coupling** and **High Cohesion**

- Coupling: Dependence

- Cohesion: Work tgt

Design Priciple:
**SOLID**
- Single responsiblity: 1 class should have 1 responsibility
- Open-closed principle: Open for extensions and Close for modification
- Liskov substitution principle: Subtypes must be substitutable for base types
- Interface segregaation principle: Many specific interfaces are better than 1 general inteface.
- Dependency inversion principle: Depend on abstracts (abstract class or interface) not implementation.

**Design Priciple #1**: The Principle of Least Knowledge or Law of Demeter.
Reduce interaction between objects to few close "friend".

**Rule 1**

Method M in object O can call other method N in object O

```java
public class O {
    public void methodM() {
        this.methodN();
    }

    public void methodN() {
        // do smth
    }
}
```

**Rule 2**

Method M in object O can call other method of parameters passed to method M

```java
public class O {
    public methodM(Friend f) {
        f.N();
    }
}

public class Friend {
    public void N() {
        // do smth
    }
}
```

**Rule 3**

Method M can call method N from another object if that object is instantiated within method M

```java
public class O {
    public void M() {
        Friend f = new Friend();
        f.N();
    }
}

public class Friend {
    public void N() {
        // do smth
    }
}
```

**Rule 4**

Method M in object O can call other method fron another object if that object is a direct component of O

```java
public class O {
    public Friend f = new Friend();

    public void M() {
        f.N();
    }
}

public class Friend() {
    public void N() {
        // do smth
    }
}
```

**Design Priciple #2**: LSP (Liskov substitution principle)

Example 3D Board is not substitutable for Board since non of the methods for Board work correctly in 3D.

```java
public class Board {
    width: int
    height: int
    titles: Title[][]


    getTitle(int, int): Tile
    addUnit(Unit, int, int)
    removeUnit(Unit, int, int)
    removeUnits(int, int)
    getUnits(int, int): List
}
```

```java
public class 3DBoard {
    zpos: int
    3dtTitles: Title[][][]


    getTitle(int, int, int): Tile
    addUnit(Unit, int, int, int)
    removeUnit(Unit, int, int, int)
    removeUnits(int, int, int)
    getUnits(int, int, int): List
}
```

**Rule for Overriding Method**
- Overrring method must be wider for argument than the previous.

- Overriding method must be narrower for return than the previous.