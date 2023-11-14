package com.example.endpoint_task.service.impl;

import com.example.endpoint_task.entity.MonitoredEndpoint;
import com.example.endpoint_task.entity.User;
import com.example.endpoint_task.repository.MonitoredEndpointRepository;
import com.example.endpoint_task.service.MonitoringResultService;
import com.example.endpoint_task.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MonitoredEndpointServiceImplTest {

    @Mock
    private MonitoredEndpointRepository monitoredEndpointRepository;

    @Mock
    private UserService userService;

    @Mock
    private MonitoringResultService monitoringResultService;

    private MonitoredEndpointServiceImpl monitoredEndpointService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        monitoredEndpointService = new MonitoredEndpointServiceImpl(monitoredEndpointRepository, userService, monitoringResultService);
    }

    @Test
    public void getAllMonitoredEndpoints() {
        List<MonitoredEndpoint> expectedEndpoints = Collections.singletonList(new MonitoredEndpoint());
        when(monitoredEndpointRepository.findAll()).thenReturn(expectedEndpoints);
        List<MonitoredEndpoint> result = monitoredEndpointService.getAllMonitoredEndpoints();
        verify(monitoredEndpointRepository, times(1)).findAll();
        assertEquals(expectedEndpoints, result);
    }

    @Test
    public void testGetAllMonitoredEndpointsForUser() {
        String accessToken = "93f39e2f-80de-4033-99ee-249d92736a25";
        User user = new User();
        List<MonitoredEndpoint> expectedEndpoints = new ArrayList<>();
        when(userService.getUserByAccessToken(UUID.fromString(accessToken))).thenReturn(user);
        when(monitoredEndpointRepository.findByOwner(user)).thenReturn(expectedEndpoints);
        List<MonitoredEndpoint> actualEndpoints = monitoredEndpointService.getAllMonitoredEndpointsForUser(accessToken);
        assertEquals(expectedEndpoints, actualEndpoints);
    }

    @Test
    public void testCreateMonitoredEndpoint() {
        MonitoredEndpoint monitoredEndpoint = new MonitoredEndpoint();
        monitoredEndpointService.createMonitoredEndpoint(monitoredEndpoint);
        verify(monitoredEndpointRepository, times(1)).save(monitoredEndpoint);
    }

    @Test
    public void testUpdateMonitoredEndpoint() {
        Long id = 1L;
        MonitoredEndpoint updatedEndpoint = new MonitoredEndpoint();
        when(monitoredEndpointRepository.findById(id)).thenReturn(Optional.of(new MonitoredEndpoint()));
        monitoredEndpointService.updateMonitoredEndpoint(id, updatedEndpoint);
        verify(monitoredEndpointRepository, times(1)).save(any(MonitoredEndpoint.class));
    }

    @Test
    public void testDeleteMonitoredEndpoint() {
        Long id = 1L;
        doNothing().when(monitoringResultService).deleteMonitoringResultsForEndpoint(id);
        monitoredEndpointService.deleteMonitoredEndpoint(id);
        verify(monitoringResultService, times(1)).deleteMonitoringResultsForEndpoint(id);
        verify(monitoredEndpointRepository, times(1)).deleteById(id);
    }

}
