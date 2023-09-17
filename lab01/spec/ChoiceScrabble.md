# 📚 Choice Exercise: Scrabble Subwords

A new version of the popular board game [Scrabble](https://en.wikipedia.org/wiki/Scrabble) is coming out soon, and they want you to write a program to calculate the score for a word on the board.

In this new version of Scrabble, instead of the score for a word placement being the sum of the values of each individual letter, the score for the word is the number of distinct 'subwords' it contains, including itself; if the world is not a subword, its score is 0.

The definition of a subword is recursive: a subword is constructed by removing one letter from a word. For a subword to be valid, it must be in our dictionary and have at least 2 letters. **The subwords for a word should only be recursively generated if the word itself is a valid subword**.

Let's have a look at an example. The six subwords for the word lion are `ion`, `lin`, `in`, `io`, `li` and `on`. (in our dictionary), and so the final score is `7`. Note that even though `lo` is made up of the letters of `lion` and is valid dictionary word, it is not counted because there is not path of subwords from `lion` to `lo`.

Inside [`Scrabble.java`](/src/main/java/scrabble/Scrabble.java), your `Scrabble` class should have a constructor that takes in a word, and a method `score` which returns the score for the word.

We have provided you with the list of dictionary words.

Your program should find subwords in a case-insensitive manner.

We have provided a complete suite of JUnit tests for you in [`src/test/java/scrabble/ScrabbleTest.java`](/src/test/java/scrabble/ScrabbleTest.java).

## Examples

```java
Scrabble s1 = new Scrabble("lion");
System.out.println(s1.score()); // 7
Scrabble s2 = new Scrabble("bread");
System.out.println(s2.score()); // 18
```

<details>
  <summary>Hint</summary>
  Recursion is your friend
</details>

> Once completed **commit** and **push** your changes to GitLab.

Problem sourced from Grok Learning NCSS Challenge (Advanced), 2016.
