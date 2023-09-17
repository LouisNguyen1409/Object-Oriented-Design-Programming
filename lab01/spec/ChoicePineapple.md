# 🍕 Choice Exercise: Pineapple on Piazza

In this exercise you are going to (start to) implement your own forum! This week you'll complete a simplified version of the program that involves two classes interacting with one another.

We have designed an Object-Oriented solution to the following requirements, with class and function stubs in the [`src/main/java/unsw/piazza`](/src/main/java/unsw/piazza/) package. Your task is to implement the functions according to the specification.

- `Post`
  - At the moment, a post is just a string containing a message.
- `Thread`
  - A `Thread` is created with a title, and a first post;
  - Posts can be added to the thread
  - A `Thread` has a series of tags, which are just strings. These tags should be retrieved in a sorted fashion (as per the documentation on `getTags`).
- `PiazzaForum`
  - The `PiazzaForum` contains a list of threads;
  - Users can search for threads by tag;

## ArrayLists

In Java as well as Arrays, there are `ArrayLists` - objects akin to a list in python that stores a collection of objects that can be dynamically added to and removed from. You can initialise an `ArrayList` using the following snippet:

```java
List<String> shoppingCart = new ArrayList<String>();
```

## Testing

We have provided a complete suite of tests for you in the [`src/test/java/unsw/piazza/PiazzaTest.java`](/src/test/java/unsw/piazza/PiazzaTest.java) file. You will have to uncomment the whole file in order to run the test. Don't worry too much about how these tests work for now - you just need to get them passing.

You can put in debugging print statements by using `System.err.println("message");`, and these can be viewed in the **Debug Console on VSCode**.

> Once completed **commit** and **push** your changes to GitLab.
