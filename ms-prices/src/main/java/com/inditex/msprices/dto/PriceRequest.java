package com.inditex.msprices.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceRequest {
    private String date;
    private Long productId;
    private Long brandId;
}
