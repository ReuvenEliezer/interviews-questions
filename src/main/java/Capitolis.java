import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Capitolis {

    @Test
    public void createFileDirectoryArchitecture() {
        //create file directory architecture
        Directory mainDirectory = new Directory(new Prop("mainDirectory", null));
        File file = new File("content1".getBytes(), new Prop("file1", mainDirectory));
        file.addContent("addContent".getBytes());
        Assert.assertEquals("content1addContent", new String(file.content, StandardCharsets.UTF_8));
        File file2 = new File("content2".getBytes(), new Prop("file2", mainDirectory));
        List<Content> contents = mainDirectory.contents;
        Assert.assertEquals(2, contents.size());
        Directory subDirectory = new Directory(new Prop("subDirectory", mainDirectory));
        Assert.assertEquals(3, contents.size());
    }

    class Prop {
        String name;
        Directory parentDir;
        LocalDateTime createDateTime;
        LocalDateTime updatedDateTime;

        public Prop(String name, Directory parentDir) {
            this.name = name;
            this.parentDir = parentDir;
            this.createDateTime = LocalDateTime.now();
            this.updatedDateTime = this.createDateTime;
        }
    }

    abstract class Content {
        Prop prop;
    }

    class File extends Content {
        byte[] content;

        public File(byte[] content, Prop prop) {
            this.content = content;
            this.prop = prop;
            this.prop.parentDir.contents.add(this);
        }

        public void addContent(byte[] bytes) {
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                output.write(this.content);
                output.write(bytes);
                this.content = output.toByteArray();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            this.prop.updatedDateTime = LocalDateTime.now();
        }

        public void replaceContent(byte[] bytes) {
            this.content = bytes;
            this.prop.updatedDateTime = LocalDateTime.now();
        }
    }

    class Directory extends Content {
        List<Content> contents = new ArrayList<>();

        public Directory(Prop prop) {
            this.prop = prop;
            if (this.prop.parentDir != null) {
                this.prop.parentDir.contents.add(this);
            }
        }
    }


    @Test
    public void printAllCombinationsForPlacing20IdenticalBallsIn10Cells() {
        /**
         *
         * 20 0 0 0 0 0 0 0 0 0
         * 19 1 0 0 0 0 0 0 0 0
         * 19 0 1 0 0 0 0 0 0 0
         * .
         * 0 10 0 0 8 0 0 0 2 0
         * .
         * 0 0 0 0 0 0 0 0 0 20
         *
         * ----------
         *
         * 20 balls in 2 cells
         *
         * 20 0
         * 19 1
         * 18 2
         * .
         * 1 19
         * 0 20
         * ------------
         *
         * Pseudocode
         *public void printAllCombinations (int balls, int cells){
         * 	if (cells<0) return;
         * 	for (int i = 0; i<balls; i++){
         * 		print(balls-i)
         * 		printAllCombinations(i, cells-1);
         *        }
         * }
         *
         * 20 balls in 3 cells
         *
         * 20 0 0
         * 19 1 0
         * 19 0 1
         * 18 1 1
         * 18 2 0
         * .
         * 1 19
         * 0 20
         * ------------
         */

        StringBuilder sb = new StringBuilder();
        int cells = 5;
        int balls = 3;
        int[] arr = new int[cells];
        printAllCombinations(balls, cells, arr, sb);
        System.out.println(sb);
    }

    private void printArr(int[] arr, StringBuilder sb) {
        for (Integer i : arr) {
            sb.append(i).append(" ");
        }
        sb.append(System.lineSeparator());
    }

    public void printAllCombinations(int balls, int cells, int[] arr, StringBuilder sb) {
        if (cells == 0) {
            if (balls == 0) {
                printArr(arr, sb);
            }
            return;
        }
        for (int i = balls; i >= 0; i--) {
            arr[arr.length - cells] = i;
            printAllCombinations(balls - i, cells - 1, arr, sb);
        }
    }
}
