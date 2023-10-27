# 🚦 Core Exercise: Traffic Light

In this lab, you will be implementing the state pattern to simulate a traffic light using a simplified version of actuated traffic light algorithm.

The simple traffic light has three states, `Red Light`, `Green Light` and `Yellow Light`.

- **Red Light**: it lasts `x` ticks and then changes to **Green Light**
- **Green Light**: it lasts `y` ticks and then changes to **Yellow Light**
- **Yellow Light**: it lasts `z` tick and then changes to **Red Light**

**The actuated traffic light algorithm**

The actuated traffic light algorithm works by dynamically changing the duration of green, yellow, and red lights based on the current traffic demand at the intersection. It gives longer green lights to the approaches with higher traffic demand and adjusts the red light and yellow light timing accordingly.

In this lab, we will only focus on one traffic light for one approach and define the traffic demand to be `numOfCars + numOfPedestrians`. The algorithm works as follows:

1. By default, the red light lasts **6** ticks, the green light lasts **4** ticks and the yellow light lasts **1** tick.
2. The duration change only happens when changing lights. In other words, if the traffic demand is higher than 100 during a green light, the duration remains unchanged.
3. When the traffic demand is higher than 100, the green light duration is changed to **6** ticks, otherwise, it remains at **4** ticks.
4. When the traffic demand is lower than 10, the red light duration changes to **10** ticks, otherwise, it remains at **6** ticks.
5. The yellow light duration doesn’t change.

# Task 1) State Pattern

Have a look at the code in [`src/main/java/trafficlight/TrafficLight.java`](/src/main/java/trafficlight/TrafficLight.java). At the moment the code isn’t very good as it is a bunch of if statements and is an example of the code smell [Switch Statements](https://refactoring.guru/smells/switch-statements).

In a blog post design your implementation of the state pattern before implementing it:

- What are the different states?
- What are the actions/transitions between each state?

Draw a **State Diagram** or a **State Table** and put it in your blog post to answer the questions.

Implement the State Pattern to remove this code smell, making sure the functionality remains the same.

There are some regression tests in [`src/test/java/trafficlight/TrafficLightTest.java`](/src/test/java/trafficlight/TrafficLightTest.java). They should remain passing after you implement the State Pattern.

In [`blog.md`](/blog.md) write a brief explanation for each of the following questions.

1. How does the initial code break the open/closed principle, and how does the state pattern fix it?
2. What is the difference between the Strategy Pattern and the state pattern? What makes this an example of the State Pattern?

Make sure you note any questions you have for your tutor/lab assist in your blog post :slight_smile:

# Task 2) Extending the Traffic Light System - Pedestrian Light

It is dangerous to have pedestrians and vehicles moving at the same time. There’s a new requirement to allow the traffic light to go into a state for pedestrians to walk across the road. The behaviour of the traffic light changes as the following

- `reportState` method of Pedestrian light returns `Pedestrian light`
- When it is changing from the **Red Light**,
  - If there’s at least 1 pedestrian waiting at the crossing, it changes to **Pedestrian Light** and then goes from **Pedestrian Light** to **Green Light**
  - If there’s no pedestrian waiting at the crossing, it changes directly to **Green Light**
- The Pedestrian light always lasts 2 ticks.

Update your solution to meet the new requirement, and update your State Diagram / State Table accordingly. The failed tests should now pass after your implementation.
