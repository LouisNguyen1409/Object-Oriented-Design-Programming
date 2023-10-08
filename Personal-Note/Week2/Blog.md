"Has a" relationship can be written as **Aggreagation** relationship or **Composition** relationship

There are 6 types of arrows in UML:
- Dependency (example people reading a book. Therefore, the status of the book is complete or uncomplete depends on the people)

- Aggregation: Class contains another class (example course contains students)

- Composition: Class contains another class. However, the contained class cannot exist outside of the container class (example the leg of the chair or the brain in person)

- Association/ Directed association: Class uses another class in some way (example students learns from tutors)

- Inheritance

- Realization: Class implements an interface.


For access modifiers in UML we have 4 symbols:
- "+" for Public

- "-" for Private

- "#" protected

- "~" package local

Design by Contact (DbC):
- Pre-condition can be **weaker** but can not be **stronger**
- Post-condition can be **stronger** but cannot be **weaker**
- Invariant maintain the condition is true for for both pre and post condition (example mark for pre and post condition need to be from 0 to 100 - input and output need to match invariant)