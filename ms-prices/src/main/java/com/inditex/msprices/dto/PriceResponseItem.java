package com.inditex.msprices.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PriceResponseItem {
    private Long productId;
    private Long brandId;
    private String brandName;
    private Long priceList;
    private String startDate;
    private String endDate;
    private String finalPrice;
}
