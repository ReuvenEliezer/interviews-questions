import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VeeTest {

    /**
     * Write a function, that receives as argument a path to location on HD, and calculate the size of all video files in that path.
     * <p>
     * FIle(String path)
     * boolean isVideo()
     * boolean isDir()
     * String getPath()
     * int getSize()
     * List<File> getChildren()
     */
    @Test
    public void test() {
        Path fullPath = Paths.get("C:\\Users\\eliez\\IdeaProjects\\Interviews-Questions\\src\\main\\resources");
        int videoSize = calcVideoSize(fullPath.toString());
    }

    private int calcVideoSize(String fullPath) {
        return calcVideoSizeRecursive(new File(fullPath), 0);
    }

    private int calcVideoSizeRecursive(File file, int videoFileSize) {
        if (file.isDirectory()) {
            for (File file1 : file.listFiles()) {
                videoFileSize = calcVideoSizeRecursive(file1, videoFileSize);
            }
        } else {
            videoFileSize++;
        }
        return videoFileSize;
    }
}
