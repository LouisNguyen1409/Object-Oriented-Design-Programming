**Enrolments**

Q1: What is the code smell present in this snippet? What is the design problem causing the smell?

Code smell: Inappropriate use of string comparison for grade checking.
Method compares the grade.getGrade() value using == is checking for reference equality instead of .equals() which is checking for content equality.

Refactoring steps:

-   Change from grade.getGrade() != "FL" to !grade.getGrade().equals("FL")
-   Change from grade.getGrade() != "UF" to !grade.getGrade().equals("UF")
-   Create new method in Grade object and return boolean value based on the Grade object.

Design decisions
This will ensure the method is checking for the content of the String and ensure Single responsiblity.

Q2: Find this code, and in your blog note down the code smells you detected which led you to your conclusion.
The code is

```java
    if (enrolment.getGrade().getMark() >= 50 && enrolment.getGrade().getGrade() != "FL"
            && enrolment.getGrade().getGrade() != "UF") {
        valid = true;
        break;
    }
```

Code smell: It accesses properties and methods of objects beyond its immediate collaborators.

Refactoring steps:

-   Change it using enrolment.hasPassedCourse() method.

Q3: Find this code, and in your blog note down the code smells you detected which led you to your conclusion.

Code smell: CourseOffering is not substitutable for Course since non of the methods for Course work correctly in CourseOffering.

Reflect on the Enrolments Lab:

Challenging: Try to find code smells.
New things: Get to know how to identify the code violation of the Law of Demeter and LSP.
Improve code: Try to apply 2 new laws when needed to avoid code smells.

**The Bank's Contract**

- Preconditions are checks that ensure that the input to a method is valid, and these checks are implemented in both the BankAccount and LoggedBankAccount classes. Postconditions specify the expected behavior of a method, and the methods in both classes fulfill their specified postconditions.

- Balance being greater than or equal to 0 is a class invariant for both classes because it is a condition that must always hold true for an instance of BankAccount or LoggedBankAccount if the preconditions of the methods are met. Whenever a deposit or withdrawal operation is performed, it ensures that the balance remains non-negative (precondition check), and the postconditions guarantee that the balance will not become negative as a result of these operations.

- Yes. LoggedBankAccount is a subclass of BankAccount, and it maintains the same methods as its parent class while adding additional logging functionality.