package com.voxloud.provisioning.service;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.exception.ResourceNotFoundException;
import com.voxloud.provisioning.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProvisioningServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private ProvisioningServiceImpl provisioningService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProvisioningFileWithEmptyOverrideFragment() throws Exception {
        Device device = new Device();
        device.setMacAddress("00:1B:44:11:3A:B7");
        device.setModel(Device.DeviceModel.DESK);
        device.setOverrideFragment("");
        device.setUsername("user123");
        device.setPassword("pass123");

        when(deviceRepository.findByMacAddress("00:1B:44:11:3A:B7")).thenReturn(device);

        assertDoesNotThrow(() -> {
            String result = provisioningService.getProvisioningFile("00:1B:44:11:3A:B7");
            System.out.println(result);
        });

        verify(deviceRepository, times(1)).findByMacAddress("00:1B:44:11:3A:B7");
    }

    @Test
    void testGetProvisioningFileDeviceNotFound() {
        when(deviceRepository.findByMacAddress("00:1B:44:11:3A:B7")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            provisioningService.getProvisioningFile("00:1B:44:11:3A:B7");
        });

        verify(deviceRepository, times(1)).findByMacAddress("00:1B:44:11:3A:B7");
    }
}


