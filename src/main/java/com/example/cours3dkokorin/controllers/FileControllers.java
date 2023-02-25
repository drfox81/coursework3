package com.example.cours3dkokorin.controllers;

import com.example.cours3dkokorin.services.FileServices;
import com.example.cours3dkokorin.services.ProductServices;
import com.example.cours3dkokorin.services.impl.SocksServicesImpl;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")
public class FileControllers {

    private FileServices fileServices;
    private ProductServices socksServices;

    public FileControllers(FileServices fileServices, ProductServices socksServices) {
        this.fileServices = fileServices;
        this.socksServices = socksServices;
    }

    @GetMapping("/getALL")
    public ResponseEntity<?> getAllSocks() {
        Path path = socksServices.getAllSocks();
        try {
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            } else {
                try {
                    InputStreamResource resource =
                            new InputStreamResource(new FileInputStream(path.toFile()));
                    return ResponseEntity.ok().contentLength(Files.size(path)).
                            contentType(MediaType.TEXT_PLAIN).header(HttpHeaders.CONTENT_DISPOSITION,
                                    "attachment;filename=\"" + path + "-report.txt\"").
                            body(resource);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
