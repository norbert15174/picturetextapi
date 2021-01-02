package pl.picturetextapi.controllers;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/post")
public class PictureController {

 //   @GetMapping
    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile){
        ITesseract instance = new Tesseract();
        instance.setDatapath("src\\main\\resources\\static\\lib");

        try {
            File file = File.createTempFile("src\\main\\resources\\static\\lol\\",multipartFile.getOriginalFilename());
            try {
                multipartFile.transferTo(file);
                try {
                    String result = instance.doOCR(file);
                    return result;
                } catch (TesseractException e) {
                    System.err.println(e.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "it doesn't work";


    }


}
