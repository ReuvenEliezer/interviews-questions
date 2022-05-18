package UpSolverStorage;

import org.junit.Test;
import org.thymeleaf.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class UpSolverTest {


    /**
     * https://upsolver.slite.com/p/note/fbGuc3HquW95bEcuL_klOD
     * <p>
     * Coding Exercise- BlobSource
     * This exercise aims to test your ability to write working, readable and testable code. The exercise is given as best effort. Try to finish it, but a partial solution might also be good enough.
     * ​
     * ​
     * A blob (binary large object) is the equivalent of a file in the cloud storage world (such as S3 or Azure Blob Storage). In Upsolver, our processing is based on reading and writing blobs from the customer's cloud storage.
     * ​
     * A blob storage has the following functionalities:
     * Read- Read a blob from a path
     * Write- Write a blob to a path.
     * if the file already exists, overwrite it.
     * If the parent directories don't exist, create them.
     * List- List all blobs and directories under a path
     * ListRecursively- List all blobs, directories and the blobs under those directories under a path.
     * ​
     * Implement a blob storage module over your local RAM. Show that it works by running it from a simple main function.
     * Write one test for each method. There's no need for a special testing library, each test can be a simple function.
     * ​
     * Restrictions:
     * External libraries are not allowed.
     * Using the disk is not allowed.
     */

    @Test
    public void readFileTest() {
//        Path fullPath = Paths.get("C:\\Users\\eliez\\IdeaProjects\\Interviews-Questions\\src\\main\\resources\\");
        Path fullPath = Paths.get("C:\\Users\\fileName1");
        File content = new File("fileName1".getBytes(StandardCharsets.UTF_8), "fileName1");
        Storage storage = new StorageImpl();
        storage.write(fullPath.toString(), content);

        Content result = storage.read(fullPath.toString());
        assertNotNull(result);
        assertEquals(content, result);
    }

    @Test
    public void readFileOverrideFile_AddDirAndListAndListRecursiveTest() {
        Path fullPath = Paths.get("C:\\Users\\fileName1");
        File content = new File("fileName1".getBytes(StandardCharsets.UTF_8), "fileName1");
        Storage storage = new StorageImpl();
        storage.write(fullPath.toString(), content);

        Content result = storage.read(fullPath.toString());
        assertNotNull(result);
        assertEquals(content, result);


        File content2 = new File("fileName1Override".getBytes(StandardCharsets.UTF_8), "fileName1");
        storage.write(fullPath.toString(), content2);

        Content result2 = storage.read(fullPath.toString());
        assertNotNull(result2);
        assertTrue(result2 instanceof File);
        File file = (File) result2;
        String value = new String(file.getContent(), StandardCharsets.UTF_8);
        assertEquals(new String(content2.getContent(), StandardCharsets.UTF_8), value);
        assertEquals(content2, result2);


        //create new dir
        Path fullPath3 = Paths.get("C:\\Users\\Directory");
        Content content3 = new Directory("Directory");
        storage.write(fullPath3.toString(), content3);

        Content result3 = storage.read(fullPath3.toString());
        assertNotNull(result3);
        assertTrue(result3 instanceof Directory);
        assertEquals(content3.name, result3.name);


        List<Content> list = storage.list(Paths.get("C:\\").toString());
        assertEquals(1, list.size());
//        assertEquals(List.of(content3), list);

        List<Content> listRecursively = storage.listRecursively(Paths.get("C:\\").toString());
        assertEquals(3, listRecursively.size());
    }

    @Test
    public void listAndListRecursiveTest() {
        Storage storage = new StorageImpl();
        Path fullPath = Paths.get("C:\\Users\\eliez\\IdeaProjects\\Interviews-Questions\\src\\main\\resources\\fileName1.scv");
        storage.write(fullPath.toString(), new File("fileName1".getBytes(StandardCharsets.UTF_8), "fileName1.scv"));
        List<Content> listRecursively = storage.listRecursively(Paths.get("C:\\").toString());
        assertEquals(8, listRecursively.size());
        String[] split = StringUtils.split(fullPath, "\\");
        for (int i = 1; i < split.length; i++) {
            assertEquals(split[i], listRecursively.get(i - 1).name);
        }
//        IntStream.range(1, split.length).boxed().collect(Collectors.toList());
    }

    @Test
    public void readDirectoryTest() {
        Storage storage = new StorageImpl();
        Path fullPath = Paths.get("C:\\Users\\Directory");
        Content content = new Directory("Directory");
        storage.write(fullPath.toString(), content);

        Content result = storage.read(fullPath.toString());
        assertNotNull(result);
        assertTrue(result instanceof Directory);
        assertEquals(content.name, result.name);
    }

    @Test
    public void deleteTest() {
        Storage storage = new StorageImpl();
        Path fullPath = Paths.get("C:\\Users\\Directory");
        Content content = new Directory("Directory");
        storage.write(fullPath.toString(), content);
        storage.write(fullPath.toString() + 1, content);

        assertEquals(2, storage.list("C:\\Users").size());
        storage.delete(fullPath.toString());
        assertEquals(1, storage.list("C:\\Users").size());
    }

    @Test(expected = NoSuchElementException.class)
    public void readFileNotExistPathTest() {
        Storage storage = new StorageImpl();
        Path fullPath = Paths.get("C:\\Users\\fileName1");
        File content = new File("fileName1".getBytes(StandardCharsets.UTF_8), "fileName1");
        storage.write(fullPath.toString(), content);
        storage.read(fullPath.toString() + 1);
    }

    @Test(expected = NoSuchElementException.class)
    public void readFileNotFoundTest() {
        Storage storage = new StorageImpl();
        Path fullPath = Paths.get("C:\\Users\\fileName1");
        storage.read(fullPath.toString());
    }


}
