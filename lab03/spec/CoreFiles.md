# 📂 Core Exercise: Files

Inside [`src/main/java/unsw`](/src/main/java/unsw), there is a folder `archaic_fs` and `test` that mocks a very simple file system and tests it respectively. Three tests are already written in there. `archaic_fs` simulates a 'linux' like [inode](https://en.wikipedia.org/wiki/Inode) system. You do not need to understand how it works under the hood (it simply mocks the typical linux commands). The code is also arguably written quite poorly, and in later weeks we will look at refactoring it.

The following commands are available:

<table>
  <tr>
    <th><b>Function</b></th>
    <th><b>Behaviour</b></th>
    <th><b>Exceptions</b></th>
  </tr>
  <tr>
    <td><code>cd(path)</code></td>
    <td>
      <a href="https://man7.org/linux/man-pages/man1/cd.1p.html"
        >Change Directory</a
      >
    </td>
    <td>
      <ul>
        <li>
          Throws <code>UNSWNoSuchFileException</code> if part of the path cannot
          be found
        </li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><code>mkdir(path, createParentDirectories, ignoreIfExists)</code></td>
    <td>
      <a href="https://man7.org/linux/man-pages/man1/mkdir.1.html"
        >Make Directory</a
      >
    </td>
    <td>
      <ul>
        <li>
          Throws <code>UNSWFileNotFoundException</code> if a part of the path
          cannot be found and <code>createParentDirectories</code> is false
        </li>
        <li>
          Throws <code>UNSWFileAlreadyExistsException</code> if the folder
          already exists and <code>ignoreIfExists</code> is false
        </li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><code>writeToFile</code></td>
    <td>
      Writes <code>content</code> to a file at <code>path</code><br />
      <ul>
        <li>
          Options are a EnumSet of FileWriteOptions, e.g.
          <code
            >EnumSet.of(FileWriteOptions.APPEND, FileWriteOptions.CREATE)</code
          >
        </li>
        <li>
          The full set is <code>CREATE</code>, <code>APPEND</code>,
          <code>TRUNCATE</code>, <code>CREATE_IF_NOT_EXISTS</code>
        </li>
      </ul>
    </td>
    <td>
      <ul>
        <li>
          Throws <code>UNSWFileNotFoundException</code> if the file cannot be
          found and no creation options are specified
        </li>
        <li>
          Throws <code>UNSWFileAlreadyExistsException</code> if the file already
          exists and <code>CREATE</code> is true.
        </li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><code>readFromFile(path)</code></td>
    <td>Returns the content for a given file.</td>
    <td>
      <ul>
        <li>
          Throws <code>UNSWFileNotFoundException</code> if the file cannot be
          found
        </li>
      </ul>
    </td>
  </tr>
</table>

Your task is to:

1. Create the `UNSWFileNotFoundException` and `UNSWFileAlreadyExistsException`, `UNSWNoSuchFileExceptionException` types in the [`exceptions`](/src/main/java/unsw/archaic_fs/exceptions/) package. They can simply inherit their Java counterparts (`java.io.FileNotFoundException`, `java.nio.file.FileAlreadyExistsException` and `java.nio.file.NoSuchFileException`). **Currently, the code does not compile as these exceptions have not been implemented.**
2. Complete the suite of integration tests for the system. You will need at least 80% code coverage (see below). Make sure to test both success and error conditions.

# Coverage Checking

For this exercise, we require that your JUnit tests give at least 80% coverage on your code. In this course we will be using a coverage checker called JaCoCo through Gradle.

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

- The coverage checking report will be in: `build/reports/jacoco/test/html/index.html`
- The test report will be in: `build/reports/tests/test/index.html`

You can also run `bash extract_coverage.sh` which will extract the coverage from the HTML and print it out.

> You must run `gradle test` first. This bash file **only works for Linux based systems**.
