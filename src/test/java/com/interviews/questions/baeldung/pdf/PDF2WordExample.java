package com.interviews.questions.baeldung.pdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Bidi;

public class PDF2WordExample {

    private static final String FILENAME = "src/main/resources/a.pdf";

    public static void main(String[] args) {
        try {
            generateDocFromPDF(FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateDocFromPDF(String filename) throws IOException {
        XWPFDocument doc = new XWPFDocument();

        String pdf = filename;
        PdfReader reader = new PdfReader(pdf);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);

        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            TextExtractionStrategy strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
            String text = strategy.getResultantText();
            text = reverseHebrewTextSolution(text);
            XWPFParagraph p = doc.createParagraph();
            XWPFRun run = p.createRun();
            run.setText(text);
            run.addBreak(BreakType.PAGE);
        }
        FileOutputStream out = new FileOutputStream("src/output/pdf.docx");
        doc.write(out);
        out.close();
        reader.close();
        doc.close();
    }

    /**
     * https://stackoverflow.com/questions/44136960/hebrew-arabic-yiddish-text-is-written-in-reverse-order-in-pdfbox-2-0-5
     */
    private static String reverseHebrewTextSolution(String word) {
        Bidi bidi = new Bidi(word, -2);
        if (!bidi.isMixed() && bidi.getBaseLevel() == 0)
            return word;

        int runCount = bidi.getRunCount();
        byte[] levels = new byte[runCount];
        Integer[] runs = new Integer[runCount];

        for (int result = 0; result < runCount; ++result) {
            levels[result] = (byte) bidi.getRunLevel(result);
            runs[result] = Integer.valueOf(result);
        }

        Bidi.reorderVisually(levels, 0, runs, 0, runCount);
        StringBuilder bidiText = new StringBuilder();

        for (int i = 0; i < runCount; ++i) {
            int index = runs[i].intValue();
            int start = bidi.getRunStart(index);
            int end = bidi.getRunLimit(index);
            byte level = levels[index];
            if ((level & 1) != 0) {
                while (true) {
                    --end;
                    if (end < start) {
                        break;
                    }

                    char character = word.charAt(end);
                    bidiText.append(character);
                }
            } else {
                bidiText.append(word, start, end);
            }
        }
        return bidiText.toString();
    }
}
