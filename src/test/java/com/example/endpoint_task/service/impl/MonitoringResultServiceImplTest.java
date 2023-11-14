package com.example.endpoint_task.service.impl;

import com.example.endpoint_task.entity.MonitoredEndpoint;
import com.example.endpoint_task.entity.MonitoringResult;
import com.example.endpoint_task.entity.User;
import com.example.endpoint_task.repository.MonitoredEndpointRepository;
import com.example.endpoint_task.repository.MonitoringResultRepository;
import com.example.endpoint_task.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MonitoringResultServiceImplTest {

    @Mock
    private MonitoringResultRepository monitoringResultRepository;

    @Mock
    private MonitoredEndpointRepository monitoredEndpointRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private MonitoringResultServiceImpl monitoringResultService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllMonitoringResults() {
        String accessToken = "dcb20f8a-5657-4f1b-9f7f-ce65739b359e";
        User user = new User();
        List<MonitoredEndpoint> monitoredEndpoints = Arrays.asList(
                new MonitoredEndpoint(),
                new MonitoredEndpoint(),
                new MonitoredEndpoint()
        );
        List<MonitoringResult> monitoringResults = Arrays.asList(
                new MonitoringResult(),
                new MonitoringResult(),
                new MonitoringResult()
        );
        when(userService.getUserByAccessToken(any(UUID.class))).thenReturn(user);
        when(monitoredEndpointRepository.findByOwner(any(User.class))).thenReturn(monitoredEndpoints);
        for (MonitoredEndpoint endpoint : monitoredEndpoints) {
            when(monitoringResultRepository.findTop10ByMonitoredEndpointOrderByDateOfCheckDesc(eq(endpoint)))
                    .thenReturn(monitoringResults);
        }
        List<MonitoringResult> result = monitoringResultService.getAllMonitoringResults(accessToken);
        assertNotNull(result);
        assertEquals(3 * monitoredEndpoints.size(), result.size());
    }

    @Test
    public void testCreateMonitoringResult() {
        LocalDateTime now = LocalDateTime.now();
        MonitoringResult monitoringResult = new MonitoringResult();
        monitoringResult.setDateOfCheck(now);
        monitoringResult.setReturnedHttpStatusCode(200);
        monitoringResult.setReturnedPayload("OK");
        MonitoredEndpoint endpoint = new MonitoredEndpoint();
        endpoint.setId(1L);
        monitoringResult.setMonitoredEndpoint(endpoint);
        when(monitoringResultRepository.save(any(MonitoringResult.class))).thenReturn(monitoringResult);
        monitoringResultService.createMonitoringResult(monitoringResult);
        verify(monitoringResultRepository, times(1)).save(eq(monitoringResult));
        MonitoringResult savedResult = monitoringResultRepository.save(monitoringResult);
        assertNotNull(savedResult);
        assertEquals(now, savedResult.getDateOfCheck());
        assertEquals(200, savedResult.getReturnedHttpStatusCode());
        assertEquals("OK", savedResult.getReturnedPayload());
        assertEquals(endpoint, savedResult.getMonitoredEndpoint());
    }

    @Test
    public void testCreateMonitoringResultWithNullEndpoint() {
        LocalDateTime now = LocalDateTime.now();
        MonitoringResult monitoringResult = new MonitoringResult();
        monitoringResult.setDateOfCheck(now);
        monitoringResult.setReturnedHttpStatusCode(200);
        monitoringResult.setReturnedPayload("OK");
        when(monitoringResultRepository.save(any(MonitoringResult.class))).thenReturn(monitoringResult);
        monitoringResultService.createMonitoringResult(monitoringResult);
        verify(monitoringResultRepository, times(1)).save(eq(monitoringResult));
        MonitoringResult savedResult = monitoringResultRepository.save(monitoringResult);
        assertNotNull(savedResult);
        assertEquals(now, savedResult.getDateOfCheck());
        assertEquals(200, savedResult.getReturnedHttpStatusCode());
        assertEquals("OK", savedResult.getReturnedPayload());
        assertNull(savedResult.getMonitoredEndpoint());
    }
}
