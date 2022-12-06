package com.inditex.msprices.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.inditex.msprices.entity.Brand;
import com.inditex.msprices.entity.Price;
import com.inditex.msprices.exception.ErrorCatalog;
import com.inditex.msprices.exception.PriceException;
import com.inditex.msprices.repository.BrandRepository;
import com.inditex.msprices.repository.PriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private PriceRepository priceRepository;

    ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public void run(String... args) throws Exception {
        log.info("Inicializando ingreso de informacion");

        try {
            Optional<List<Brand>> brands = getObjectsFromResource(Brand.class, "brandsInitialData.json");
            brandRepository.saveAll(brands.get());

            Optional<List<Price>> prices = getObjectsFromResource(Price.class, "pricesInitialData.json");
            priceRepository.saveAll(prices.get());
        } catch (Exception ex) {
            log.error(ErrorCatalog.ERROR_INICIALIZACION_DATOS.getLogError(), ex);
            throw new PriceException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCatalog.ERROR_INICIALIZACION_DATOS.getError());
        } finally {
            log.info("Finalizando ingreso de informacion");
        }
    }

    private <T> Optional<List<T>> getObjectsFromResource(Class<T> clazz, String resource) throws IOException {
        File file = ResourceUtils.getFile(String.format("classpath:%s",resource));
        InputStream in = new FileInputStream(file);
        CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return Optional.of(new ObjectMapper().registerModule(new JavaTimeModule()).readValue(in, javaType));
    }
}
