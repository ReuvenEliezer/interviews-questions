package UpSolverStorage;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

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
    public void test() {
//        Path fullPath = Paths.get("C:\\Users\\eliez\\IdeaProjects\\Interviews-Questions\\src\\main\\resources\\");
        Path fullPath = Paths.get("C:\\Users\\fileNameContent1");

        byte[] content = "fileNameContent1".getBytes();
//        String fileName = "fil717717371eName1.csv";

        Storage storage = new StorageImpl();
        storage.write(fullPath.toString(), content);

        byte[] result = storage.read(fullPath.toString());
        Assert.assertNotNull(result);
        Assert.assertEquals(content, result);

    }

}
