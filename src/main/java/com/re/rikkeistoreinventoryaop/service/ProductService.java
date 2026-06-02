package com.re.rikkeistoreinventoryaop.service;

import com.re.rikkeistoreinventoryaop.dto.CreateProductRequest;
import com.re.rikkeistoreinventoryaop.entity.Product;
import com.re.rikkeistoreinventoryaop.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(CreateProductRequest request) {
        Product product = productRepository.save(new Product(request.name(), request.quantity(), request.sku()));
        log.info("Đã tạo hàng hóa sku={}, quantity={}", product.getSku(), product.getQuantity());
        return product;
    }
}
