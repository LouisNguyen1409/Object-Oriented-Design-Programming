**Design decisions and reason:**
- To manage the storage, retrieval, and manipulation of Entity objects in the data store, I create a Data class that establishes an one-to-one Directed Association relationship with the BlackoutController object. This design allows the Data class to assume full responsibility for handling Entity objects, including their storage, retrieval, and manipulation.

- To establish a common interface for Device and Satellite subclasses, I create an abstract class called Entity. This class includes functions that operate at the Entity level, as well as abstract functions that Device and Satellite subclasses must implement. To store Entity objects in a HashMap with entityId as the key and Entity object as the value, I establish an one-to-many Aggregation relationship between the Entity class and the Data object.

- To manage file information and facilitate the transfer of file data between entities, I create a File class that includes all the necessary fields and methods. The File class establishes a one-to-many Aggregation relationship with the Entity class, as the Entity class will store multiple File objects in a List of File type. This design allows for efficient storage and retrieval of file data within the context of the project.

- To establish a common interface for individual Satellite types, I create an abstract class called Satellite. This class includes methods that are implemented and abstract methods that are specific to each Satellite type. The Satellite class also stores constant variables for each individual Satellite type. To inherit common fields and methods from the Entity class, which serves as the superclass for both Device and Satellite, I establish an Inheritance relationship between the Satellite class and the Entity class.

- To establish a common interface for individual Device types, I create an abstract class called Device. This class includes methods that are overwritten from the superclass and a common constructor for each individual Device type. The Device class also inherits common data fields and methods from the Entity class, which serves as the superclass for both Device and Satellite. This design allows for efficient management of Device objects within the context of the project.

- To establish a common interface for individual Satellite types, I create three small subclasses called Relay, Standard, and Teleporting. These classes inherit from the Satellite class and overwrite the abstract move methods, as each object will have a different move algorithm. Additionally, unique connection device, range and velocity, bandwidth requires a class to overwrite the methods. This design allows for efficient management of Satellite objects within the context of the project.

- To establish a common interface for individual Device types, I create three subclasses called Desktop, Handheld, and Laptop. These classes inherit from the Device class and overwrite the range data fields, as each device type will have a unique range of connection. This design allows for efficient management of Device objects within the context of the project.

**Challenges:**
- Designing a program in an object-oriented way has proven to be challenging, especially since this is my first time working on an OOP assignment.

- To ensure that my program adheres to best practices, I must carefully consider how to design classes that follow the Law of Demeter, Liskov Substitution Principle, and SOLID principles. This requires thoughtful planning and attention to detail to ensure that the program is efficient, maintainable, and scalable.

- Task 2c has proven to be extremely challenging, as it requires managing numerous objects and ensuring that they communicate with each other correctly. This task demands careful attention to both logic and design to ensure that the program functions as intended.

- Task 2c has proven to be a valuable tool in uncovering hidden bugs and ensuring that all methods work correctly. By checking all other methods, this task has brought to light many potential problems and helped me to refine my program.

**Learn:**
- Through my experience working with OOP programs, I have gained a deeper understanding of its concepts and how to effectively implement them. This knowledge has allowed me to approach programming challenges with greater confidence and efficiency.

- Through my experience with software development, I have gained knowledge and proficiency in coding software that follows specific design patterns. This expertise allows me to approach programming challenges with a structured and efficient approach, resulting in high-quality software that is both maintainable and scalable.

- Through my experience with software development, I have gained knowledge and proficiency in creating relationships between multiple classes.

