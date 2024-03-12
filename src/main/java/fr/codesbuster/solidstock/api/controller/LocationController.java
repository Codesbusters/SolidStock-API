package fr.codesbuster.solidstock.api.controller;

import fr.codesbuster.solidstock.api.entity.LocationEntity;
import fr.codesbuster.solidstock.api.payload.LocationDto;
import fr.codesbuster.solidstock.api.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/add")
    public ResponseEntity<LocationEntity> addLocation(@RequestBody LocationDto locationDto) {

        if (locationDto == null) {
            return ResponseEntity.badRequest().build();
        }

        if (locationDto.getName() == null || locationDto.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (locationDto.getDescription() == null || locationDto.getDescription().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (locationDto.getPosition() == null || locationDto.getPosition().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setName(locationDto.getName());
        locationEntity.setDescription(locationDto.getDescription());
        locationEntity.setPosition(locationDto.getPosition());

        log.info("LocationEntity: " + locationEntity);

        locationEntity = locationService.createLocation(locationEntity);

        return ResponseEntity.ok(locationEntity);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<LocationEntity>> getAllLocation() {
        Iterable<LocationEntity> locationEntities = locationService.getLocations();
        return ResponseEntity.ok(locationEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationEntity> getLocation(@PathVariable Long id) {
        LocationEntity locationEntity = locationService.getLocation(id);
        return ResponseEntity.ok(locationEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationEntity> updateLocation(@PathVariable Long id, @RequestBody LocationDto locationDto) {
        LocationEntity locationEntity = locationService.getLocation(id);
        locationEntity.setName(locationDto.getName());
        locationEntity.setDescription(locationDto.getDescription());
        locationEntity.setPosition(locationDto.getPosition());
        locationEntity = locationService.updateLocation(locationEntity);
        return ResponseEntity.ok(locationEntity);
    }

}