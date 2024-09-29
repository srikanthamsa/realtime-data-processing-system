package com.example.processing.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "processed_data")
public class ProcessedData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalData;
    private String transformedData;
    private LocalDateTime processedAt;

    private int dataLength;
    private boolean valid;

    public ProcessedData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalData() {
        return originalData;
    }

    public void setOriginalData(String originalData) {
        this.originalData = originalData;
    }

    public String getTransformedData() {
        return transformedData;
    }

    public void setTransformedData(String transformedData) {
        this.transformedData = transformedData;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "ProcessedData{" +
                "id=" + id +
                ", originalData='" + originalData + '\'' +
                ", transformedData='" + transformedData + '\'' +
                ", processedAt=" + processedAt +
                ", dataLength=" + dataLength +
                ", valid=" + valid +
                '}';
    }
}
