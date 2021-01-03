package pl.picturetextapi.controllers;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/post")
public class PictureController {


    private CloudVisionTemplate cloudVisionTemplate;
    private ResourceLoader resourceLoader;
    @Autowired
    public PictureController(CloudVisionTemplate cloudVisionTemplate, ResourceLoader resourceLoader) {
        this.cloudVisionTemplate = cloudVisionTemplate;
        this.resourceLoader = resourceLoader;
    }

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

    @EventListener(ApplicationReadyEvent.class)
    public void asd(){
        String text = this.cloudVisionTemplate.extractTextFromImage(resourceLoader.getResource("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQui3lNoQWGNN50Q2AwoiAnXnel3IpMx6auTA&usqp=CAU"));
        System.out.println(text);

    }




}
