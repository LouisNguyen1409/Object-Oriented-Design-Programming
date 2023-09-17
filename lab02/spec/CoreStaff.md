# 🔱 Core Exercise: Staff

In your lab02 repository, modify the class [`StaffMember`](/src/main/java/staff/StaffMember.java) (inside package [`staff`](/src/main/java/staff)) so that it satisfies the following requirements:

## Class Fields

The class needs fields to store the staff member’s name, salary, hire date, and end date.

<details>
  <summary>Hint</summary>
  <ul>
    <li>Java has in-built date types such as [LocalDate](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalDate.html).</li>
  </ul>
</details>

## Constructors

We now want to be able to instantiate staff members to use them in our system. We create object instances through the constructor.

**Define a constructor(s)** for the staff member that allows us to create one with the appropriate information set. Think about what state newly created staff members should be in. For example, do we ever want to create a staff member without a name?

## Getter Methods

We use getters to interact with the information the object holds i.e., to read the information in the fields without making the fields public. Think about what getters the class should have - what information do we want to access from the class? Is there information which is only used internally in the class?

## Setter Methods

Setters will allow us to update the state of private fields, potentially adding in extra logic in the process. Think carefully about what fields should have setters and which shouldn’t. Are there any fields that don’t need to change after the staff member has been created?

If one is not needed, it is better not to add it. If requirements change in the future, it is easy to add. If we add one and later decide to take it away, this is more difficult.

<details>
  <summary>Hint</summary>
  Remember that you can still set the value of a field
  <b>without a setter</b> using the <b>constructor</b> when the
  object is created.
</details>

## Overridden `toString()` method

Have a look at the Javadoc for this method first to see what its purpose is: [API reference for Java Platform, Standard Edition](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Object.html).

`toString()` is a method on the `Object` class. Navigate to the `toString()` method to read its documentation.

`toString()` returns a string representation of the object. Think about what sort of information you'd want to include about the staff member if you are **constructing a string that represents it**.

<details>
  <summary>Hint</summary>
  <ul>
    <li>
      No real right or wrong answers for this one. The definition of
      <code>toString()</code> is intentionally vague to offer you a lot of
      discretion in what the string representation of your object looks like,
      but a good idea would be to put all of the fields of the staff member into
      a string in some reasonably formatted way.
    </li>
  </ul>
</details>

## Overridden `equals(...)` methods

Have a look at the Javadoc for this method first to see what its purpose is: [API reference for Java Platform, Standard Edition](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Object.html).

`equals()` is a method on the Object class. Navigate to the `equals()` method to read its documentation.

The equals method defines what it means for your staff member to be considered equal to another object. Have a think about when two different instances of staff member’s would be considered equal.

<details>
  <summary>Hint</summary>
  <ul>
    <li>
      When are strings in C considered equal? <code>==</code> can’t be used
      because that compares pointers, but two strings in C with different
      pointers can be considered equal if... ????
    </li>
  </ul>
</details>

## JavaDoc

Add JavaDoc for all non-overriding methods and constructors.

## Inheritance

Create a sub-class of `StaffMember` called `Lecturer` in the same package with the following requirements:

- An attribute to store the school (e.g. CSE) that the lecturer belongs to
- An attribute that stores the academic status of the lecturer (e.g A for an Associate Lecturer, B for a Lecturer, and C for a Senior Lecturer)
- Appropriate getters and setters.
- An overriding `toString()` method that includes the school name and academic level.
  <br/>
  <details>
    <summary>Hint</summary>
    As a subclass, a Lecturer is a more specific type of staff member. They still
    have the information a staff member has like a name and salary etc. We would
    also like this information in the toString() for Lecturer. You've already
    written code that does this part in the staff member. Is there any way you can
    reuse this code without having to copy it? Is there any way we can call the
    code written in our parent class, from the subclass?
  </details>
- An overriding equals(...) method. See documentation [here](<https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/lang/Object.html#equals(java.lang.Object)>) for expected behaviour.<br/>
  <details>
    <summary>Hint</summary>
    Since Lecturer is a subclass of StaffMember, they also have a name and salary
    etc. For Lecturers to be considered equal, not only do the extra fields added
    to the Lecturer class need to be the same, but so to do the fields that come
    from the StaffMember class, like name and salary etc.
    <br />
    However, you’ve already written code that checks whether the StaffMember
    fields are equal. Is there any way you can reuse this code without having to
    copy it? Is there any way we can call the code written in our parent class,
    from the subclass?
</details>

- Javadoc for all non-overriding methods and constructors.

## Testing

Using [`src/test/java/staff/StaffTest.java`](/src/test/java/staff/StaffTest.java) create some tests that contain:

- A method `printStaffDetails(...)` that takes a `StaffMember` as a parameter and invokes the `toString()` method on it to print the details of the staff.
- A `main(...)` method that:
- Creates a `StaffMember` with a name and salary of your choosing.
- Creates an instance of `Lecturer` with a name, salary, school and academic status of your choosing.
- Calls `printStaffDetails(...)` twice with each of the above as arguments.
- Tests the `equals(...)` method of the two classes.

<details>
  <summary>Hint</summary>
  Read the Javadoc for the equals method to get a better understanding of what
  the equals method is required to do. You will notice that certain properties
  need to hold for the equals method to be considered correct. If you’ve done
  discrete maths before, you may recognize these as the properties of an
  equivalence relation (i.e., equality is an equivalence relation).
  <br />
  Dividing your tests up to examine each property separately is a good way to
  structure your tests.
</details>

You do not need to write any JUnit tests for this exercise (though you can if you would like).
