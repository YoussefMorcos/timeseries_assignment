package com.example.backend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

//endpoints

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class TSController {

    private final TSService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        if (files.isEmpty()) {
            return ResponseEntity.badRequest().body("No files uploaded");
        }

        service.processFiles(files);
        return ResponseEntity.ok("Files uploaded and processed successfully");
    }

    @GetMapping("/files")
    public ResponseEntity<List<String>> listFiles() {
        List<String> sources = service.getAllSources();
        log.warn("Fetched file sources: {}", sources);
        return ResponseEntity.ok(sources);
    }
}
