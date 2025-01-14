package com.example.StockPredictionTest.serviceTest;

import com.example.StockPrediction.model.StockData;
import com.example.StockPrediction.service.CsvLoaderService;
import org.springframework.core.io.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class TestCsvLoaderService {

    @Test
    void testLoadCsvFiles() throws Exception {
        Resource mockResource = Mockito.mock(Resource.class);

        InputStream mockInputStream = new ByteArrayInputStream(
                ("""
                        FLTR,25-10-2023,17085.00
                        FLTR,26-10-2023,17170.43
                        FLTR,27-10-2023,17256.28
                        FLTR,28-10-2023,17083.72
                        FLTR,29-10-2023,17152.05
                        FLTR,30-10-2023,17375.03
                        FLTR,31-10-2023,17583.53
                        FLTR,01-11-2023,17671.45
                        FLTR,02-11-2023,17865.83
                        FLTR,03-11-2023,18008.76
                        FLTR,04-11-2023,18008.76
                        FLTR,05-11-2023,18206.85
                        FLTR,06-11-2023,18206.85""").getBytes(StandardCharsets.UTF_8)
        );

        when(mockResource.getInputStream()).thenReturn(mockInputStream);

        CsvLoaderService service = new CsvLoaderService();
        ReflectionTestUtils.setField(service, "csvResources", new Resource[]{mockResource});

        service.loadCsvFiles();

        Map<String, List<StockData>> stockDataByFile =
                (Map<String, List<StockData>>) ReflectionTestUtils.getField(service, "stockDataByFile");

        List<StockData> myDataTest = stockDataByFile.get(mockResource.getFilename());

        assertNotNull(stockDataByFile,
                "StockDataByFile should not be null after loadin");

        assertEquals(13, myDataTest.size());

        StockData stockDataFirstRow = myDataTest.get(0);
        assertEquals("25-10-2023",stockDataFirstRow.getTimestamp(), "Timestamp not match");
        assertEquals(17085.00, stockDataFirstRow.getPrice(), "Price not match");

        for(StockData data : myDataTest) {
            assertEquals("FLTR", data.getStockExchangeName(), "Stock exchange name not match");
        }

    }
}
