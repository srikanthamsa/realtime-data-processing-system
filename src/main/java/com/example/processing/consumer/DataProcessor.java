package com.example.processing.consumer;

import com.example.processing.model.ProcessedData;
import com.example.processing.repository.ProcessedDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DataProcessor {

    @Autowired
    private ProcessedDataRepository repository;

    @KafkaListener(topics = "incoming-data", groupId = "data-processing-group")
    public void consume(String message) {
        System.out.println("Received message: " + message);

        if (filterCondition(message)) {

            String transformedData = transformData(message);

            int dataLength = aggregateDataLength(message);

            ProcessedData data = new ProcessedData();
            data.setOriginalData(message);
            data.setTransformedData(transformedData);
            data.setProcessedAt(LocalDateTime.now());
            data.setDataLength(dataLength);
            data.setValid(true);

            repository.save(data);
            System.out.println("Processed and saved data: " + data);
        } else {
            System.out.println("Message filtered out: " + message);
        }
    }

    private boolean filterCondition(String data) {
        return data != null && !data.trim().isEmpty();
    }

    private String transformData(String data) {
        return new StringBuilder(data).reverse().toString().toUpperCase();
    }

    private int aggregateDataLength(String data) {
        return data.length();
    }
}
