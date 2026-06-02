package com.re.rikkeistoreinventoryaop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.re.rikkeistoreinventoryaop.dto.CreateKeeperRequest;
import com.re.rikkeistoreinventoryaop.dto.CreateProductRequest;
import com.re.rikkeistoreinventoryaop.dto.InventoryRequest;
import com.re.rikkeistoreinventoryaop.entity.Product;
import com.re.rikkeistoreinventoryaop.entity.WarehouseKeeper;
import com.re.rikkeistoreinventoryaop.repository.ProductRepository;
import com.re.rikkeistoreinventoryaop.repository.WarehouseKeeperRepository;
import com.re.rikkeistoreinventoryaop.service.InventoryService;
import com.re.rikkeistoreinventoryaop.service.ProductService;
import com.re.rikkeistoreinventoryaop.service.WarehouseKeeperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:inventory-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class InventoryServiceIntegrationTests {
    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WarehouseKeeperService warehouseKeeperService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseKeeperRepository warehouseKeeperRepository;

    private WarehouseKeeper keeper;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        warehouseKeeperRepository.deleteAll();
        keeper = warehouseKeeperService.create(new CreateKeeperRequest("Nguyen Van A", "NV001"));
    }

    @Test
    void importsAndExportsStockUsingSku() {
        Product product = productService.create(new CreateProductRequest("Keyboard", 10L, "KB001"));

        inventoryService.importStock(new InventoryRequest("KB001", 4L, keeper.getId()));
        inventoryService.exportStock(new InventoryRequest("KB001", 3L, keeper.getId()));

        Product updatedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertThat(updatedProduct.getQuantity()).isEqualTo(11L);
    }

    @Test
    void rejectsExportWhenRequestedQuantityExceedsCurrentStock() {
        productService.create(new CreateProductRequest("Mouse", 2L, "MS001"));

        assertThatThrownBy(() ->
                inventoryService.exportStock(new InventoryRequest("MS001", 3L, keeper.getId())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Số lượng xuất hàng vượt quá tồn kho hiện tại!");
    }

    @Test
    void returnsOnlyProductsWithQuantityBelowFive() {
        productService.create(new CreateProductRequest("Mouse", 4L, "MS001"));
        productService.create(new CreateProductRequest("Keyboard", 5L, "KB001"));

        assertThat(inventoryService.findLowStock())
                .extracting(Product::getSku)
                .containsExactly("MS001");
    }
}
