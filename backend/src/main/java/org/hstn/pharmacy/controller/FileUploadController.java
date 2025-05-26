package org.hstn.pharmacy.controller;

import lombok.RequiredArgsConstructor;
import org.hstn.pharmacy.service.fileService.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/upload")
public class FileUploadController {

    private final FileService service;

    @PostMapping("/upload")
    public ResponseEntity<String> fileUpload(@RequestParam("uploadFile") MultipartFile uploadFile){
        service.uploadFile(uploadFile);
        return ResponseEntity.ok("Файл успешно загружен");
    }
}
