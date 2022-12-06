package com.inditex.msprices.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PriceResponse {
    private List<PriceResponseItem> items;
}