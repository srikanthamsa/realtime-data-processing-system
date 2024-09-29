// DataProcessorTest.java
package com.example.processing;

import com.example.processing.consumer.DataProcessor;
import com.example.processing.model.ProcessedData;
import com.example.processing.repository.ProcessedDataRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
public class DataProcessorTest {

    @Mock
    private ProcessedDataRepository repository;

    @InjectMocks
    private DataProcessor dataProcessor;

    @Test
    public void testConsume() {
        String testMessage = "Test Message";

        dataProcessor.consume(testMessage);

        verify(repository, times(1)).save(any(ProcessedData.class));
    }

    @Test
    public void testFilterCondition() {

    }

    @Test
    public void testTransformData() {

    }
}
