package com.re.rikkeistoreinventoryaop.controller;

import com.re.rikkeistoreinventoryaop.dto.InventoryRequest;
import com.re.rikkeistoreinventoryaop.entity.Product;
import com.re.rikkeistoreinventoryaop.service.InventoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void importStock(@Valid @RequestBody InventoryRequest request) {
        inventoryService.importStock(request);
    }

    @PostMapping("/export")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void exportStock(@Valid @RequestBody InventoryRequest request) {
        inventoryService.exportStock(request);
    }

    @GetMapping("/low-stock")
    public List<Product> findLowStock() {
        return inventoryService.findLowStock();
    }
}
