// DataQueryService.java
package com.example.processing.service;

import com.example.processing.model.ProcessedData;
import com.example.processing.repository.ProcessedDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DataQueryService {

    @Autowired
    private ProcessedDataRepository repository;

    public List<ProcessedData> getDataProcessedAfter(LocalDateTime timestamp) {
        return repository.findByProcessedAtAfter(timestamp);
    }

    public List<ProcessedData> getValidData() {
        return repository.findByValid(true);
    }

    public List<ProcessedData> searchTransformedData(String keyword) {
        return repository.findByTransformedDataContaining(keyword.toUpperCase());
    }
}
