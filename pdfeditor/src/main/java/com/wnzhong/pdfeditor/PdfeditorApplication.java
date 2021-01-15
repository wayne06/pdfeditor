package com.wnzhong.pdfeditor;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@SpringBootApplication
@RestController
public class PdfeditorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdfeditorApplication.class, args);
    }


    @PostMapping("/api/extract")
    @CrossOrigin
    public void extract(@RequestBody ExtractBody body) {
        String path = body.getPath().replaceAll("\\\\", "\\\\\\\\");
        int from = body.getFrom();
        int to = body.getTo();
        Document document = null;
        PdfCopy copy = null;
        try {
            PdfReader reader = new PdfReader(path);
            int n = reader.getNumberOfPages();
            if(to==0){
                to = n;
            }
            ArrayList<String> savepaths = new ArrayList<>();
            String savepath = path.split("\\.")[0] + "_extract.pdf";
            savepaths.add(savepath);
            document = new Document(reader.getPageSize(1));
            copy = new PdfCopy(document, new FileOutputStream(savepaths.get(0)));
            document.open();
            for(int j=from; j<=to; j++) {
                document.newPage();
                PdfImportedPage page = copy.getImportedPage(reader, j);
                copy.addPage(page);
            }
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch(DocumentException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/api/merge")
    @CrossOrigin
    public void merge(@RequestBody MergeBody body) throws IOException, DocumentException {
        String path1 = body.getPath1().replaceAll("\\\\", "\\\\\\\\");;
        String path2 = body.getPath2().replaceAll("\\\\", "\\\\\\\\");;


        Document document = new Document(new PdfReader(path1).getPageSize(1));
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(path1.split("//.")[0] + "_merge.pdf"));
        document.open();

        PdfReader reader1 = new PdfReader(path1);
        int n1 = reader1.getNumberOfPages();
        for (int j = 1; j <= n1; j++) {
            document.newPage();
            PdfImportedPage page = copy.getImportedPage(reader1, j);
            copy.addPage(page);
        }

        PdfReader reader2 = new PdfReader(path2);
        int n2 = reader2.getNumberOfPages();
        for (int j = 1; j <= n2; j++) {
            document.newPage();
            PdfImportedPage page = copy.getImportedPage(reader2, j);
            copy.addPage(page);
        }
        document.close();
    }

}
