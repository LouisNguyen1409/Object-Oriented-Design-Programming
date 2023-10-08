package unsw.archaic_fs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import unsw.archaic_fs.exceptions.UNSWFileAlreadyExistsException;
import unsw.archaic_fs.exceptions.UNSWFileNotFoundException;
import unsw.archaic_fs.exceptions.UNSWNoSuchFileException;
import java.util.EnumSet;

public class ArchaicFsTest {
    @Test
    public void testCdInvalidDirectory() {
        ArchaicFileSystem fs = new ArchaicFileSystem();

        // Try to change directory to an invalid one
        assertThrows(UNSWNoSuchFileException.class, () -> {
            fs.cd("/usr/bin/cool-stuff");
        });
    }

    @Test
    public void testCdValidDirectory() {
        ArchaicFileSystem fs = new ArchaicFileSystem();

        assertDoesNotThrow(() -> {
            fs.mkdir("/usr/bin/cool-stuff", true, false);
            fs.cd("/usr/bin/cool-stuff");
        });
    }

    @Test
    public void testCdAroundPaths() {
        ArchaicFileSystem fs = new ArchaicFileSystem();

        assertDoesNotThrow(() -> {
            fs.mkdir("/usr/bin/cool-stuff", true, false);
            fs.cd("/usr/bin/cool-stuff");
            assertEquals("/usr/bin/cool-stuff", fs.cwd());
            fs.cd("..");
            assertEquals("/usr/bin", fs.cwd());
            fs.cd("../bin/..");
            assertEquals("/usr", fs.cwd());
        });
    }

    @Test
    public void testCreateFile() {
        ArchaicFileSystem fs = new ArchaicFileSystem();

        assertDoesNotThrow(() -> {
            fs.writeToFile("test.txt", "My Content", EnumSet.of(FileWriteOptions.CREATE, FileWriteOptions.TRUNCATE));
            assertEquals("My Content", fs.readFromFile("test.txt"));
            fs.writeToFile("test.txt", "New Content", EnumSet.of(FileWriteOptions.TRUNCATE));
            assertEquals("New Content", fs.readFromFile("test.txt"));
        });
    }

    // Now write some more!
    // Some ideas to spark inspiration;
    // - File Writing/Reading with various options (appending for example)
    // - Cd'ing .. on the root most directory (shouldn't error should just remain on
    // root directory)
    // - many others...

    @Test
    public void testInvalidMakeDirectory1() {
        ArchaicFileSystem fs = new ArchaicFileSystem();
        assertThrows(UNSWFileNotFoundException.class, () -> {
            fs.mkdir("/abc/cde/efg", false, false);
        });
    }

    @Test
    public void testInvalidMakeDirectory2() {
        ArchaicFileSystem fs = new ArchaicFileSystem();
        assertThrows(UNSWFileAlreadyExistsException.class, () -> {
            fs.mkdir("/usr/bin/cool-stuff", true, false);
            fs.mkdir("/usr/bin/cool-stuff", true, false);
        });
    }

    @Test
    public void testValidMakeDirectory() {
        ArchaicFileSystem fs = new ArchaicFileSystem();
        assertDoesNotThrow(() -> {
            fs.mkdir("/usr/bin/cool-stuff", true, false);
        });
    }

    @Test
    public void testValidReadFile() {
        ArchaicFileSystem fs = new ArchaicFileSystem();
        assertDoesNotThrow(() -> {
            fs.writeToFile("test.txt", "My Content", EnumSet.of(FileWriteOptions.CREATE, FileWriteOptions.TRUNCATE));
            assertEquals("My Content", fs.readFromFile("test.txt"));
        });
    }

    @Test
    public void testInvalidReadFile() {
        ArchaicFileSystem fs = new ArchaicFileSystem();
        assertThrows(UNSWFileNotFoundException.class, () -> {
            fs.readFromFile("test.txt");
        });
    }

    @Test
    public void testValidWriteFile() {
        ArchaicFileSystem fs = new ArchaicFileSystem();
        assertDoesNotThrow(() -> {
            fs.writeToFile("test.txt", "My Content", EnumSet.of(FileWriteOptions.CREATE, FileWriteOptions.TRUNCATE));
            assertEquals("My Content", fs.readFromFile("test.txt"));
        });
    }

    @Test
    public void testInvalidWriteFile1() {
        ArchaicFileSystem fs = new ArchaicFileSystem();
        assertThrows(UNSWFileNotFoundException.class, () -> {
            fs.writeToFile("test.txt", "My Content", EnumSet.of(FileWriteOptions.TRUNCATE));
        });
    }

    @Test
    public void testInvalidWriteFile2() {
        ArchaicFileSystem fs = new ArchaicFileSystem();
        assertThrows(UNSWFileAlreadyExistsException.class, () -> {
            fs.writeToFile("test.txt", "My Content", EnumSet.of(FileWriteOptions.CREATE, FileWriteOptions.TRUNCATE));
            fs.writeToFile("test.txt", "My Content", EnumSet.of(FileWriteOptions.CREATE, FileWriteOptions.TRUNCATE));
        });
    }

    @Test
    public void testValidAppendFile() {
        ArchaicFileSystem fs = new ArchaicFileSystem();
        assertDoesNotThrow(() -> {
            fs.writeToFile("test.txt", "My Content", EnumSet.of(FileWriteOptions.CREATE, FileWriteOptions.TRUNCATE));
            fs.writeToFile("test.txt", "My Content", EnumSet.of(FileWriteOptions.APPEND));
            assertEquals("My ContentMy Content", fs.readFromFile("test.txt"));
        });
    }

    @Test
    public void testInvalidAppendFile1() {
        ArchaicFileSystem fs = new ArchaicFileSystem();
        assertThrows(UNSWFileNotFoundException.class, () -> {
            fs.writeToFile("test.txt", "My Content", EnumSet.of(FileWriteOptions.APPEND));
        });
    }

    @Test
    public void testValidAppendFile2() {
        ArchaicFileSystem fs = new ArchaicFileSystem();
        assertDoesNotThrow(() -> {
            fs.writeToFile("test.txt", "My Content", EnumSet.of(FileWriteOptions.CREATE_IF_NOT_EXISTS,
            FileWriteOptions.TRUNCATE));
            fs.writeToFile("test.txt", "My Content", EnumSet.of(FileWriteOptions.APPEND));
            assertEquals("My ContentMy Content", fs.readFromFile("test.txt"));
        });
    }

    @Test
    public void testValidAppendFile3() {
        ArchaicFileSystem fs = new ArchaicFileSystem();
        assertDoesNotThrow(() -> {
            fs.writeToFile("test.txt", "My Content", EnumSet.of(FileWriteOptions.CREATE, FileWriteOptions.TRUNCATE));
            fs.writeToFile("test.txt", "My Content", EnumSet.of(FileWriteOptions.CREATE_IF_NOT_EXISTS,
            FileWriteOptions.TRUNCATE));
        });
    }

    @Test
    public void testMakeDirectory() {
        ArchaicFileSystem fs = new ArchaicFileSystem();
        assertDoesNotThrow(() -> {
            fs.mkdir("/usr/bin/cool-stuff", true, false);
            fs.mkdir("/usr/bin/cool-stuff", true, true);
        });
    }
}
