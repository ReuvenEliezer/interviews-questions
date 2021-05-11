import org.junit.Test;

public class SortLimitedMemory {
    /**
     * https://stackoverflow.com/questions/2382820/sorting-a-huge-file-in-java
     * https://stackoverflow.com/questions/2087469/sort-a-file-with-huge-volume-of-data-given-memory-constraint
     * https://stackoverflow.com/questions/4358087/sort-with-the-limited-memory
     */

    @Test
    public void test(){
        /**
         * As other mentionned, you can process in steps.
         * I would like to explain this with my own words (differs on point 3) :
         *
         * Read the file sequentially, process N records at a time in memory (N is arbitrary, depending on your memory constraint and the number T of temporary files that you want).
         *
         * Sort the N records in memory, write them to a temp file. Loop on T until you are done.
         *
         * Open all the T temp files at the same time, but read only one record per file. (Of course, with buffers). For each of these T records, find the smaller, write it to the final file, and advance only in that file.
         */
    }
}
