package miage.nanterre.m1app.realtimekeynote.Controller;


import miage.nanterre.m1app.realtimekeynote.Service.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping(value = "/video")
public class UploadController {
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value="/upload",method= RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            UploadService.upload(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Object>("{\"success\" : true}", HttpStatus.OK);
    }
}