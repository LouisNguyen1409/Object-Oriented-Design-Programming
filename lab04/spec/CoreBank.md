# 💰 Core Exercise: The Bank's Contract

You will need to fill in [`blog.md`](/blog.md) for this activity. Put your answers to the question inside of it.

# Prerequisite Summary - What is Design by Contract?

Have a read of the summary below if you need to brush up on what Design by Contract is.

<details>
  <summary>Hint</summary>
  <ul>
    <li>
      When designing software, there are two main approaches we can take when
      considering how it may be used and the potential for it to be used
      erroneously. The first is <b>Defensive Programming</b> 🛡️, which you may
      be familiar with from other courses, even if it hasn’t been explicitly
      referred to as such. This is where we put a
      <b
        >series of checks in our code so that erroneous input doesn’t slip
        through and break something</b
      >, and we handle the error accordingly, resolving it or reporting the
      error and continue running if possible, or exiting the program if needed.
    </li>
    <li>
      The other approach which we’ve introduced in this course is
      <b>Design by Contract</b> 🥂. The idea with this is to do the opposite of
      defensive programming, we don’t want to have all these checks bloating our
      code just for exception handling. Instead, we
      <b>offload the responsibility to the user of our code</b>. We explicitly
      <b>describe how it is to be used correctly</b>, and explicitly describe
      what will happen if they use it correctly. But if they use it incorrectly,
      we offer no guarantees on what will happen. We do this by describing
      <b>preconditions</b> and <b>postconditions</b>. Preconditions are what the
      user needs to do to use it correctly, and postconditions are what can be
      expected from our code if used correctly.
    </li>
  </ul>

  <h2>What approach should I take?</h2>
  <ul>
    <li>
      Design by Contract is very useful, but you don’t want to use it for
      something that is very critical. For example, operating system code is
      very much defensive programming as it is unwise for the operating system
      to trust user input as important things could be corrupted. However, for
      an internal API in an ecosystem we may not have to worry so much about
      incorrect input, since we can configure the client of the API to always
      comply with the input standard. We make it the client’s responsibility to
      call our code correctly.
    </li>
  </ul>

  <h2>Class Invariants</h2>
  <ul>
    <li>
      As well as preconditions and postconditions, which are things associated
      with each method, there is also a notion of class invariants,
      <b>a property of the class which always holds true</b>.
    </li>
    <li>
      For example, let’s say we have a class that models a game which has a
      field for the player’s score. This score can go up or down depending on
      the player’s actions, but it can never be negative. Thus
      <code>score >= 0</code> is a class invariant. Note the requirement here is
      that the class invariant holds before and after any method call, however,
      the invariant can be temporarily violated during the processing of a
      method.
    </li>
  </ul>
</details>

# Lab Exercise

In this question, we are going to be practising Design by Contract. Think carefully about what the preconditions and postconditions of your methods will be and what the class invariants will be.

<details>
  <summary>Hint</summary>
  <ul>
    <li>
      Under design by contract, anything you have as a
      <b>precondition</b> shouldn’t be checked in the method you can
      <b>assume this condition will always be true</b>.
    </li>
    <li>
      Similarly, anything that isn’t checked in the method that the user could
      do erroneously should be in the preconditions. E.g., if you’re not
      checking for division by zero, but the user could give this as erroneous
      input, then it should be explicitly clear in the preconditions that the
      input needs to be non-zero.
    </li>
  </ul>
</details>

Consider a simple Bank system that allows for customers to have accounts. The customers can make deposits and withdrawals, and in this simplified system, an account balance is never allowed to go below 0.

Inside [`src/main/java/banking`](/src/main/java/banking), create a `BankAccount` class for maintaining a customer's bank balance.

- Each bank account should have a current balance and methods implementing deposits and withdrawals.
- Money can only be withdrawn from an account if there are sufficient funds.

In the JavaDoc for the methods, define preconditions and postconditions for the methods.

Then, create a subclass of `BankAccount` called `LoggedBankAccount`, also with the preconditions and postconditions articulated.

- Every deposit and withdrawal must make a log of the action.

Inside your blog post, answer the following questions in a few sentences:

- Explain why the code is consistent with the preconditions and postconditions.
- Explain why _balance >= 0_ is a class invariant for both classes. I.e., give a small informal proof of why this is always true if your preconditions are met.
- Are your class definitions consistent with the Liskov Substitution Principle?
