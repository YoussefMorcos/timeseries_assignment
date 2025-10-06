package com.example.backend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// methods used in the endpoints to do the job

@Service
@RequiredArgsConstructor
@Slf4j
public class TSService {

    private final TSRepository repository;

    // just in case multiple files are inserted at once
    public void processFiles(List<MultipartFile> files) {
        for (MultipartFile file : files) {
            processSingleFile(file);
        }
    }

    private void processSingleFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        // checking for file name
        if (fileName == null)
            fileName = "unknown.csv";

        // checking if file name already exists
        String baseName = fileName.replace(".csv", "");
        long existingCount = repository.countDistinctSourcesByPattern(baseName + "%");
        String finalName = existingCount == 0
                ? fileName
                : baseName + "_" + (existingCount + 1) + ".csv";
        log.warn("count: {}", existingCount);
        // reading the file
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String headerLine = reader.readLine();
            if (headerLine == null)
                return;
            String[] headers = headerLine.split(",");
            List<String> measures = Arrays.asList(headers).subList(1, headers.length);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            List<TSEntity> records = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                // splitting data lines
                String[] parts = line.split(",");
                // skipping the lines not in the right format
                if (parts.length != headers.length)
                    continue;
                // following timestamp required
                LocalDateTime ts = LocalDateTime.parse(parts[0].trim(), formatter);

                // building entities to be entereed in the db, O(n)
                for (int i = 1; i < parts.length; i++) {

                    // if a measure has an empty value, skip adding a record for it
                    String rawValue = parts[i].trim();
                    if (rawValue.isEmpty())
                        continue;

                    // if there is a measure value, a record is added
                    try {
                        double value = Double.parseDouble(rawValue);
                        records.add(TSEntity.builder()
                                .ts(ts)
                                .source(finalName)
                                .measure(measures.get(i - 1))
                                .floatValue(value)
                                .build());
                    } catch (NumberFormatException e) {
                        log.warn("non-numeric value encountered '{}' for measure '{}' in file '{}'", rawValue,
                                measures.get(i - 1), finalName);
                    }
                }
            }

            // flushing to avoid delays for large files
            if (!records.isEmpty()) {
                repository.saveAllAndFlush(records);
                records.clear();
            }

        } catch (Exception e) {
            log.error("Failed to process file: {}", finalName, e);
        }
    }

    public List<String> getAllSources() {
        return repository.findAll()
                .stream()
                .map(TSEntity::getSource)
                .distinct()
                .toList();
    }
}
