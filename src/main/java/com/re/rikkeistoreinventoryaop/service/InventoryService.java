package com.re.rikkeistoreinventoryaop.service;

import com.re.rikkeistoreinventoryaop.dto.InventoryRequest;
import com.re.rikkeistoreinventoryaop.entity.Product;
import com.re.rikkeistoreinventoryaop.repository.ProductRepository;
import com.re.rikkeistoreinventoryaop.repository.WarehouseKeeperRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    private final ProductRepository productRepository;
    private final WarehouseKeeperRepository warehouseKeeperRepository;

    public InventoryService(
            ProductRepository productRepository,
            WarehouseKeeperRepository warehouseKeeperRepository) {
        this.productRepository = productRepository;
        this.warehouseKeeperRepository = warehouseKeeperRepository;
    }

    @Transactional
    public void importStock(InventoryRequest request) {
        validateKeeper(request.keeperId());
        if (productRepository.incrementQuantityBySku(request.sku(), request.quantity()) == 0) {
            throw new IllegalArgumentException("Không tìm thấy mã SKU: " + request.sku());
        }
        log.info("Nhập kho thành công: sku={}, quantity={}, keeperId={}",
                request.sku(), request.quantity(), request.keeperId());
    }

    @Transactional
    public void exportStock(InventoryRequest request) {
        validateKeeper(request.keeperId());
        if (productRepository.decrementQuantityBySkuIfAvailable(request.sku(), request.quantity()) == 1) {
            log.info("Xuất kho thành công: sku={}, quantity={}, keeperId={}",
                    request.sku(), request.quantity(), request.keeperId());
            return;
        }
        if (!productRepository.existsBySku(request.sku())) {
            throw new IllegalArgumentException("Không tìm thấy mã SKU: " + request.sku());
        }
        throw new IllegalArgumentException("Số lượng xuất hàng vượt quá tồn kho hiện tại!");
    }

    @Transactional(readOnly = true)
    public List<Product> findLowStock() {
        List<Product> products = productRepository.findByQuantityLessThan(5L);
        log.info("Kiểm kê kho: có {} mặt hàng sắp hết", products.size());
        return products;
    }

    private void validateKeeper(Long keeperId) {
        if (!warehouseKeeperRepository.existsById(keeperId)) {
            throw new IllegalArgumentException("Không tìm thấy mã nhân viên kho: " + keeperId);
        }
    }
}
