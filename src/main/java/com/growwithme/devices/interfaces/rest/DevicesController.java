package com.growwithme.devices.interfaces.rest;

import com.growwithme.crops.application.internal.outboundservices.acl.ExternalIamService;
import com.growwithme.devices.domain.model.commands.CreateDeviceCommand;
import com.growwithme.devices.domain.model.commands.DeleteDeviceCommand;
import com.growwithme.devices.domain.model.queries.GetAllDevicesByFarmerIdQuery;
import com.growwithme.devices.domain.model.queries.GetDeviceByIdQuery;
import com.growwithme.devices.domain.model.valueobjects.DeviceType;
import com.growwithme.devices.domain.services.DeviceCommandService;
import com.growwithme.devices.domain.services.DeviceQueryService;
import com.growwithme.devices.interfaces.rest.resources.DeviceResource;
import com.growwithme.devices.interfaces.rest.transform.DeviceResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/devices", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Devices", description = "Devices Management Endpoint")
public class DevicesController {
    private final DeviceCommandService deviceCommandService;
    private final DeviceQueryService deviceQueryService;
    private final ExternalIamService externalIamService;

    @Operation(summary = "Create a new device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Device created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<DeviceResource> createDevice(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("cropId") Long cropId, @RequestParam("name") String name, @RequestParam("deviceType") DeviceType deviceType) {
        var email = userDetails.getUsername();
        var farmerId = externalIamService.fetchUserIdByEmail(email);

        if (farmerId == null) {
            throw new IllegalArgumentException("Farmer not found for email: " + email);
        }

        var createDeviceCommand = new CreateDeviceCommand(
                cropId,
                farmerId,
                name,
                deviceType
        );

        var device = deviceCommandService.handle(createDeviceCommand);

        if (device.isEmpty()) {
            throw new IllegalArgumentException("Invalid input data for creating device");
        }

        var deviceResource = DeviceResourceFromEntityAssembler.toResourceFromEntity(device.get());
        return new ResponseEntity<>(deviceResource, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a device by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Device deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @DeleteMapping("/{deviceId}")
    public ResponseEntity<?> deleteDevice(@PathVariable Long deviceId) {
        var deleteDeviceCommand = new DeleteDeviceCommand(deviceId);
        deviceCommandService.handle(deleteDeviceCommand);
        return ResponseEntity.ok("Device deleted successfully");
    }

    @Operation(summary = "Get device by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @GetMapping("/{deviceId}")
    public ResponseEntity<DeviceResource> getDeviceById(@PathVariable Long deviceId) {
        var getDeviceByIdQuery = new GetDeviceByIdQuery(deviceId);
        var device = deviceQueryService.handle(getDeviceByIdQuery);
        if (device.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var deviceResource = DeviceResourceFromEntityAssembler.toResourceFromEntity(device.get());
        return ResponseEntity.ok(deviceResource);
    }

    @Operation(summary = "Get all devices by farmer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devices retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Devices not found")
    })
    @GetMapping("/farmer")
    public ResponseEntity<List<DeviceResource>> getAllDevicesByFarmerId(@AuthenticationPrincipal UserDetails userDetails) {
        var email = userDetails.getUsername();
        var farmerId = externalIamService.fetchUserIdByEmail(email);

        if (farmerId == null) {
            return ResponseEntity.notFound().build();
        }

        var getAllDevicesByFarmerIdQuery = new GetAllDevicesByFarmerIdQuery(farmerId);
        var devices = deviceQueryService.handle(getAllDevicesByFarmerIdQuery);
        if (devices.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var deviceResources = devices.stream()
                .map(DeviceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(deviceResources);
    }

    @Operation(summary = "Get the temperature list for a device by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Temperature list retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @GetMapping("/temperature-list/{deviceId}")
    public ResponseEntity<List<Float>> getTemperatureListByDeviceId(@PathVariable Long deviceId) {
        var deviceResult = deviceQueryService.handle(new GetDeviceByIdQuery(deviceId));
        if (deviceResult.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var device = deviceResult.get();
        
        return ResponseEntity.ok(device.getTemperatureList());
    }

    @Operation(summary = "Get the humidity list for a device by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Humidity list retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @GetMapping("/humidity-list/{deviceId}")
    public ResponseEntity<List<Float>> getHumidityListByDeviceId(@PathVariable Long deviceId) {
        var deviceResult = deviceQueryService.handle(new GetDeviceByIdQuery(deviceId));
        if (deviceResult.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var device = deviceResult.get();

        return ResponseEntity.ok(device.getHumidityList());
    }

    @Operation(summary = "Patch temperature for a device by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Temperature patched successfully"),
            @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @PostMapping("/temperature-list/{deviceId}/{temperature}")
    public ResponseEntity<Float> patchTemperatureByDeviceId(@PathVariable Long deviceId, @PathVariable Float temperature) {
        var deviceResult = deviceQueryService.handle(new GetDeviceByIdQuery(deviceId));
        if (deviceResult.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var device = deviceResult.get();
        device.addTemperatureToList(temperature);
        return ResponseEntity.ok(temperature);
    }

    @Operation(summary = "Patch humidity for a device by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Humidity patched successfully"),
            @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @PostMapping("/humidity-list/{deviceId}/{humidity}")
    public ResponseEntity<Float> patchHumidityByDeviceId(@PathVariable Long deviceId, @PathVariable Float humidity) {
        var deviceResult = deviceQueryService.handle(new GetDeviceByIdQuery(deviceId));
        if (deviceResult.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var device = deviceResult.get();
        device.addHumidityToList(humidity);
        return ResponseEntity.ok(humidity);
    }
}
