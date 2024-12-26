package com.interviews.questions.baeldung.pdf;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MergePdf {

    @Test
    public void mergePdf() {
        List<File> files = collectFiles();
        mergePDFFiles(files, getStocksTickersPath("result.pdf"));
    }

    @Test
    public void mergePdfFromFolder() {
        List<File> files = readAllFiles("");
        mergePDFFiles(files, "");
    }



    public List<File> readAllFiles(String folderPath) {
        try (Stream<Path> pathStream = Files.walk(Paths.get(folderPath))) {
            // Using Files.walk to traverse the directory
            return pathStream
                    .filter(Files::isRegularFile) // Filter for regular files only
                    .map(Path::toFile) // Convert Path to File
                    .toList();
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public void mergePDFFiles(List<File> files, String mergedFileName) {
        PDFMergerUtility pdfMerger = new PDFMergerUtility();
        pdfMerger.setDestinationFileName(mergedFileName);
        for (File file : files) {
            try {
                pdfMerger.addSource(file);
                pdfMerger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly().streamCache);
            } catch (IOException e) {
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
