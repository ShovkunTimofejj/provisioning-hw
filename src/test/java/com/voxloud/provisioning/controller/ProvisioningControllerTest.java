package com.voxloud.provisioning.controller;

import com.voxloud.provisioning.exception.ResourceNotFoundException;
import com.voxloud.provisioning.service.ProvisioningService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProvisioningControllerTest {

    @Mock
    private ProvisioningService provisioningService;

    @InjectMocks
    private ProvisioningController provisioningController;

    public ProvisioningControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProvisioningFileSuccess() throws Exception {
        String macAddress = "00:11:22:33:44:55";
        String expectedResponse = "some provisioning data";

        when(provisioningService.getProvisioningFile(macAddress)).thenReturn(expectedResponse);

        ResponseEntity<String> actualResponse = provisioningController.getProvisioningFile(macAddress);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
        verify(provisioningService, times(1)).getProvisioningFile(macAddress);
    }

    @Test
    public void testGetProvisioningFileDeviceNotFound() throws Exception {
        String macAddress = "00:11:22:33:44:55";

        when(provisioningService.getProvisioningFile(macAddress)).thenThrow(new ResourceNotFoundException("GET_PROVISIONING_FILE", "RSC100", "Device not found"));

        ResponseEntity<String> actualResponse = provisioningController.getProvisioningFile(macAddress);

        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        assertEquals("Device not found", actualResponse.getBody());
        verify(provisioningService, times(1)).getProvisioningFile(macAddress);
    }

    @Test
    public void testGetProvisioningFileUnexpectedError() throws Exception {
        String macAddress = "00:11:22:33:44:55";

        when(provisioningService.getProvisioningFile(macAddress)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<String> actualResponse = provisioningController.getProvisioningFile(macAddress);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
        assertEquals("An unexpected error occurred", actualResponse.getBody());
        verify(provisioningService, times(1)).getProvisioningFile(macAddress);
    }
}


