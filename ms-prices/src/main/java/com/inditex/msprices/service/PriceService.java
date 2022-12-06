package com.inditex.msprices.service;

import com.inditex.msprices.dto.PriceRequest;
import com.inditex.msprices.dto.PriceResponseItem;
import com.inditex.msprices.exception.PriceException;

import java.util.List;

public interface PriceService {
    List<PriceResponseItem> getPrices(PriceRequest request) throws PriceException;
}
