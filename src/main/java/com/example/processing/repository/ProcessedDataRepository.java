// ProcessedDataRepository.java
package com.example.processing.repository;

import com.example.processing.model.ProcessedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProcessedDataRepository extends JpaRepository<ProcessedData, Long> {

    List<ProcessedData> findByProcessedAtAfter(LocalDateTime timestamp);

    List<ProcessedData> findByValid(boolean valid);

    List<ProcessedData> findByTransformedDataContaining(String keyword);
}
