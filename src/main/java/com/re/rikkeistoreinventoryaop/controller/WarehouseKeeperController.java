package com.re.rikkeistoreinventoryaop.controller;

import com.re.rikkeistoreinventoryaop.dto.CreateKeeperRequest;
import com.re.rikkeistoreinventoryaop.entity.WarehouseKeeper;
import com.re.rikkeistoreinventoryaop.service.WarehouseKeeperService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/keepers")
public class WarehouseKeeperController {
    private final WarehouseKeeperService warehouseKeeperService;

    public WarehouseKeeperController(WarehouseKeeperService warehouseKeeperService) {
        this.warehouseKeeperService = warehouseKeeperService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WarehouseKeeper create(@Valid @RequestBody CreateKeeperRequest request) {
        return warehouseKeeperService.create(request);
    }
}
