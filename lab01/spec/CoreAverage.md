# 🔢 Core Exercise: Average

A class is similar to a struct in the C language in that it stores related data in fields, where the fields can be of different types.

1. In your repository, open the file [`src/main/java/average/Average.java`](/src/main/java/average/Average.java). You will find a class named `Average`.
2. This class defines a method `computeAverage()` that takes in an array of integers and returns the average of these numbers. You are required to implement this method.<br/>
   Hint:
   - To complete this task, you need to compute the sum of the numbers and the total number of elements
   - Use a `for` loop to traverse the list of numbers and compute the sum of the number.
   - Use `nums.length` to get the length of the array, after the sum has been computed.
3. Next, define a main() method. <br/>
   **Note**: Every Java application needs one class with a `static void main(String[] args)` method.
   - This class is the entry point for the Java application and is the class name passed to the java command to run the application.
   - The interpreter executes the code in the `static void main(String[] args)` method when the program starts, and is the point from which all other classes and corresponding methods are invoked.
   - VSCode will recognise the `main` method and provide you with a "Run" button to run the code if you have the correct extensions installed. **Do not manually compile your code**.
   - ![Click the run button to compile & run your code](/images/AverageMainFunction.png) Click the run button to compile & run your code.
4. Inside the `main` method, initialise an array of the numbers 1 - 6 (integers, inclusive) and invoke the method `computeAverage()`, passing it as an argument. <br/>
   Hint: `computeAverage()` is an **instance** method, so you will need to create an instance of the class Average and invoke the method on this instance.
5. Assign the result of this method to a variable and print the variable in the format: `The average is {average}`.
6. You can test your code by running the test located in [`src/test/java/average/AverageTest.java`](/src/test/java/average/AverageTest.java). <br/>
   Note:
   - You must have the ["Extension Pack for Java"](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) installed in your VSCode (For both local and CSE).
   - To run the individual test in [`AverageTest.java`](/src/test/java/average/AverageTest.java), click the green arrow (or green/red tick) next to the line number of the test.
   - ![Click the icon in the red box to run the test](/images/AverageTestRun.png) Click the icon in the red box to run the test.
   - If you have any issues running the tests, see the [Setup Troubleshooting](https://nw-syd-gitlab.cseunsw.tech/COMP2511/23T3/content/-/blob/master/setup/docs/SetupTroubleshooting.md) guide which is also linked on Webcms3.
7. You can also run all the tests in the repository using `gradle test` (or `2511 gradle test` if on CSE). **This will run the tests for the next exercise as well**, so don’t worry if it says it fails the Satellite & Splitter tests. <br/>
   > If you are working locally, you must have Gradle 7.2 installed
8. You must ensure you are passing the linter for the repository. You can run the linter by using `gradle checkstyleMain` and `gradle checkstyleTest` (or `2511 gradle checkstyleMain` and `2511 gradle checkstyleTest` if on CSE). If the task fails, a list of files with linting errors will appear in the terminal.

> Once completed **commit** and **push** your changes to GitLab.

The automatic pipeline in this repository will run all the tests found under `src/test` using the `gradle test` command and the linter using `gradle checkstyleMain` and `gradle checkstyleTest`. You can view the exact Continuous Integration checks in the [`.gradle-ci.yml`](/.gitlab-ci.yml) file. Push your changes to the remote master branch to run these tests as Continuous Integration checks.

![](/images/AverageGitLabCI.png)

# Gradle Summary

## Running Gradle Tests

<table>
  <tr>
    <th>If you are working LOCALLY:</th>
    <th>If you are working on CSE:</th>
  </tr>
  <tr>
    <td><code>gradle test</code></td>
    <td><code>2511 gradle test</code></td>
  </tr>
</table>

## Running Gradle Linter

<table>
  <tr>
    <th>If you are working LOCALLY:</th>
    <th>If you are working on CSE:</th>
  </tr>
  <tr>
    <td>
      <code>gradle checkstyleMain</code><br />
      <code>gradle checkstyleTest</code>
    </td>
    <td>
      <code>2511 gradle checkstyleMain</code><br />
      <code>2511 gradle checkstyleTest</code>
    </td>
  </tr>
</table>
