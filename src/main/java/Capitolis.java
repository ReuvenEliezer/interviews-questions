import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class Capitolis {
    //    class File {
//        Prop prop;
//        byte[] content;
//    }
//
//    class Directory {
//        Prop prop;
//        List<Directory> childrenDirectories;
//        List<File> files;
//    }

    @Test
    public void test() {
        //create file directory architecture
        Directory mainDirectory = new Directory(null, new Prop("mainDirectory", null, LocalDateTime.now()));
        File file = new File("content".getBytes(), new Prop("file1", mainDirectory, LocalDateTime.now()));
//        mainDirectory.contents.add()
    }

    class Prop {
        String name;
        Directory parentDir;
        LocalDateTime createDateTime;
        LocalDateTime updatedDateTime;

        public Prop(String name, Directory parentDir, LocalDateTime createDateTime) {
            this.name = name;
            this.parentDir = parentDir;
            this.createDateTime = createDateTime;
            this.updatedDateTime = createDateTime;
        }
    }

    abstract class Content {

    }

    class File extends Content {
        byte[] content;
        Prop prop;

        public File(byte[] content, Prop prop) {
            this.content = content;
            this.prop = prop;
        }
    }

    class Directory extends Content {
        Prop prop;
        List<Content> contents;

        public Directory(List<Content> Contents, Prop prop) {
            this.contents = Contents;
            this.prop = prop;
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
