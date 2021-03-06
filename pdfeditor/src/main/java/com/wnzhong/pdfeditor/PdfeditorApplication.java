package com.wnzhong.pdfeditor;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author wayne
 */
@SpringBootApplication
@RestController
public class PdfeditorApplication {

    @Value("${pdfAddr.prefix}")
    private String pdfAddrPrefix;

    public static void main(String[] args) {
        SpringApplication.run(PdfeditorApplication.class, args);
    }


    @PostMapping("/api/upload")
    @CrossOrigin
    public String coverUpload(MultipartFile file) {
        File imgFolder = null;
        if (System.getProperty("os.name").equals("Linux")) {
            imgFolder = new File("/usr/pdf");
        } else  {
            imgFolder = new File("C:\\pdf");
        }
        File f = new File(imgFolder, getRandomString(6) + file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        try {
            file.transferTo(f);
            String filePath = f.getAbsolutePath();
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            stringBuffer.append(base.charAt(number));
        }
        return stringBuffer.toString();
    }

    @PostMapping("/api/extract")
    @CrossOrigin
    public String extract(@RequestBody ExtractBody body) {
        // windows server
        //String path = body.getPath().replaceAll("\\\\", "\\\\\\\\");
        //String path = body.getPath();

        String path = null;
        if (System.getProperty("os.name").equals("Linux")) {
            path = body.getPath();
        } else  {
            path = body.getPath().replaceAll("\\\\", "\\\\\\\\");
        }

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
            // windows server
            //String[] strings = savepath.split("\\\\");
            //return "http://localhost:8443/api/file/" + strings[strings.length-1];

            //String[] strings = savepath.split("/");

            String[] strings = null;
            if (System.getProperty("os.name").equals("Linux")) {
                strings = savepath.split("/");
            } else  {
                strings = savepath.split("\\\\");
            }

            return pdfAddrPrefix + strings[strings.length-1];
        } catch (IOException e) {
            e.printStackTrace();
        } catch(DocumentException e) {
            e.printStackTrace();
        }
        return "";
    }

    @PostMapping("/api/merge")
    @CrossOrigin
    public String merge(@RequestBody MergeBody body) throws IOException, DocumentException {
        //String path1 = body.getPath1().replaceAll("\\\\", "\\\\\\\\");;
        //String path2 = body.getPath2().replaceAll("\\\\", "\\\\\\\\");;

        String path1 = body.getPath1();
        String path2 = body.getPath2();

        //String savePath = path1.split("//.")[0] + "_merge.pdf";
        String savePath = path1.split("//.")[0] + "_merge.pdf";

        Document document = new Document(new PdfReader(path1).getPageSize(1));
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(savePath));
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

        String[] strings = null;
        if (System.getProperty("os.name").equals("Linux")) {
            strings = savePath.split("/");
        } else  {
            strings = savePath.split("\\\\");
        }
        return pdfAddrPrefix + strings[strings.length-1];
    }

}
