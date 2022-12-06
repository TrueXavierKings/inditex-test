package com.inditex.msprices.service.impl;

import com.inditex.msprices.PriceUtils;
import com.inditex.msprices.dto.PriceRequest;
import com.inditex.msprices.dto.PriceResponseItem;
import com.inditex.msprices.entity.Price;
import com.inditex.msprices.exception.ErrorCatalog;
import com.inditex.msprices.exception.PriceException;
import com.inditex.msprices.repository.PriceRepository;
import com.inditex.msprices.service.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @Value("${prices.include.date.equals}")
    private Boolean includeDateEquals;

    Function<Price, PriceResponseItem> priceToPriceResponse = price -> PriceResponseItem.builder()
            .productId(price.getProductId())
            .brandId(price.getBrand().getId())
            .brandName(price.getBrand().getName())
            .priceList(price.getPriceId())
            .startDate(PriceUtils.localDateToString(price.getStartDate()))
            .endDate(PriceUtils.localDateToString(price.getEndDate()))
            .finalPrice(PriceUtils.finalPrice(price.getPrice(), price.getCurrency()))
            .build();

    @Override
    public List<PriceResponseItem> getPrices(PriceRequest request) throws PriceException {
        log.info("Iniciando obtencion de precios - entrada: {}", request.toString());
        List<Price> prices = new ArrayList<>();
        // Consulta todos los precios al no agregar ningun criterio
        if (request.getProductId() == null && request.getBrandId() == null) {
            prices = priceRepository.findAll();
        }
        // Consulta por producto si se agrego solo el criterio de producto
        if (request.getProductId() != null && request.getBrandId() == null) {
            prices = priceRepository.findByProductId(request.getProductId());
        }
        // Consulta por producto si se agrego solo el criterio de cadena de grupo
        if (request.getProductId() == null && request.getBrandId() != null) {
            prices = priceRepository.findByBrand_Id(request.getBrandId());
        }
        // Consulta por producto si se agrego solo el criterio de producto y cadena de grupo
        if (request.getProductId() != null && request.getBrandId() != null) {
            prices = priceRepository.findByProductIdAndBrand_Id(request.getProductId(), request.getBrandId());

            // Filtro de rango de fechas de aplicacion de tarifas y seleccion de la tarifa prioritaria
            if (StringUtils.hasText(request.getDate())) {
                LocalDateTime applyDate = getLocalDateTimeFromRequest(request);
                prices = prices.stream()
                        .filter(p -> PriceUtils.isBetweenOrEqual(includeDateEquals, p.getStartDate(), p.getEndDate(), applyDate))
                        .max(Comparator.comparing(Price::getPriority))
                        .map(List::of)
                        .orElseGet(Collections::emptyList);
            }
        }

        if (CollectionUtils.isEmpty(prices)) {
            throw new PriceException(HttpStatus.NOT_FOUND, ErrorCatalog.ERROR_SIN_DATOS.getError());
        }
        List<PriceResponseItem> finalPrices = prices.stream()
                .map(p -> priceToPriceResponse.apply(p))
                .collect(Collectors.toList());
        log.info("Finalizando obtencion de precios");
        return finalPrices;
    }

    private LocalDateTime getLocalDateTimeFromRequest(PriceRequest request) throws PriceException {
        try {
            return PriceUtils.StringToLocalDateTime(request.getDate());
        } catch (DateTimeParseException dtpe) {
            log.error(ErrorCatalog.ERROR_PARSEO_FECHA_APLICACION.getLogError(), dtpe);
            throw new PriceException(HttpStatus.BAD_REQUEST, ErrorCatalog.ERROR_PARSEO_FECHA_APLICACION.getError());
        }
    }
}
