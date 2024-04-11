package baeldung.pdf;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MergePdf {

    @Test
    public void mergePdf() {
        List<File> files = collectFiles();
        mergePDFFiles(files, getStocksTickersPath("result.pdf"));
    }

    public void mergePDFFiles(List<File> files, String mergedFileName) {
        PDFMergerUtility pdfMerger = new PDFMergerUtility();
        pdfMerger.setDestinationFileName(mergedFileName);
        for (File file : files) {
            try {
                pdfMerger.addSource(file);
                pdfMerger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly().streamCache);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static String getStocksTickersPath(String fileName) {
        return "src" + File.separator + "main" + File.separator + "resources" + File.separator + "to-remove" + File.separator + fileName;
    }

    private List<File> collectFiles() {
        return List.of(
                new File(getStocksTickersPath("1.pdf")),
                new File(getStocksTickersPath("2.pdf")),
                new File(getStocksTickersPath("3.pdf")),
                new File(getStocksTickersPath("4.pdf"))
        );
    }
}
