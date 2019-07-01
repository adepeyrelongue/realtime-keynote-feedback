package miage.nanterre.m1app.realtimekeynote.Service;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Normalizer;

public class UploadService {
    public static final String uploadDir = "C:\\data-video\\";

    public static void upload( MultipartFile file) throws IOException {
        String fileName = getCleanFileName(file.getOriginalFilename());
        File convertFile = new File(uploadDir + fileName);
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
    }

    public static String getCleanFileName(String fileName){
        fileName = Normalizer.normalize(fileName, Normalizer.Form.NFD);
        fileName = fileName.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        fileName = fileName.replaceAll("\\s","");
        return fileName;
    }
}
