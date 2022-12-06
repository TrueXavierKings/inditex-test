package com.inditex.msprices.repository;

import com.inditex.msprices.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByProductId(Long productId);
    List<Price> findByBrand_Id(Long brandId);
    List<Price> findByProductIdAndBrand_Id(Long productId, Long brandId);
}
