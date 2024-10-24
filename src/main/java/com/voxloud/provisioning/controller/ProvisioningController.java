package com.voxloud.provisioning.controller;

import com.voxloud.provisioning.exception.ResourceNotFoundException;
import com.voxloud.provisioning.service.ProvisioningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ProvisioningController {

    @Autowired
    private ProvisioningService provisioningService;

    @GetMapping("/")
    public String home() {
        return "Welcome to Provisioning API!";
    }

    @GetMapping(value = "provisioning/{macAddress}")
    public ResponseEntity<String> getProvisioningFile(@PathVariable("macAddress") String macAddress) {
        log.info("Requesting provisioning file for MAC address: {}", macAddress);
        try {
            String provisioningFile = provisioningService.getProvisioningFile(macAddress);
            return ResponseEntity.ok(provisioningFile);
        } catch (ResourceNotFoundException e) {
            log.error("Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
