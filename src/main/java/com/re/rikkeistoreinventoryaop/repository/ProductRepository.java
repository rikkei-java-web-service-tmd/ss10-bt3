package com.re.rikkeistoreinventoryaop.repository;

import com.re.rikkeistoreinventoryaop.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySku(String sku);

    List<Product> findByQuantityLessThan(Long quantity);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Product p set p.quantity = p.quantity + :quantity where p.sku = :sku")
    int incrementQuantityBySku(@Param("sku") String sku, @Param("quantity") Long quantity);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            update Product p
               set p.quantity = p.quantity - :quantity
             where p.sku = :sku
               and p.quantity >= :quantity
            """)
    int decrementQuantityBySkuIfAvailable(@Param("sku") String sku, @Param("quantity") Long quantity);
}
