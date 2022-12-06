package com.inditex.msprices.controller;

import com.inditex.msprices.dto.PriceRequest;
import com.inditex.msprices.dto.PriceResponse;
import com.inditex.msprices.exception.PriceException;
import com.inditex.msprices.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PricesController {

    @Autowired
    private PriceService priceService;

    @PostMapping(value = "/prices",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PriceResponse getPrices(
            @RequestBody PriceRequest request) throws PriceException {
        return PriceResponse.builder()
                .items(priceService.getPrices(request))
                .build();
    }

}
