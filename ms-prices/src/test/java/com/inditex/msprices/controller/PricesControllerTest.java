package com.inditex.msprices.controller;

import com.inditex.msprices.dto.PriceRequest;
import com.inditex.msprices.dto.PriceResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricesControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static HttpHeaders headers;

    private JSONObject priceRequestJson;

    @BeforeEach
    public void setup() throws JSONException {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        priceRequestJson = new JSONObject();
        priceRequestJson.put("productId", 35455);
        priceRequestJson.put("brandId", 1);
    }

    @Test
    public void getAllPrices() {
        ResponseEntity<PriceResponse> pricesResponse = restTemplate.postForEntity("/prices",
                PriceRequest.builder().build(), PriceResponse.class);


        assertThat(pricesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(pricesResponse.getBody().getItems().size() == 6);
    }

    @Test
    public void test_1() throws Exception {
        priceRequestJson.put("date", "2020-06-14-10.00.00");
        HttpEntity<String> request = new HttpEntity<>(priceRequestJson.toString(), headers);

        ResponseEntity<PriceResponse> pricesResponse = restTemplate.postForEntity("/prices",
                request, PriceResponse.class);

        assertThat(pricesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(Objects.requireNonNull(pricesResponse.getBody()).getItems().get(0).getPriceList(), 1L);
    }

    @Test
    public void test_2() throws Exception {
        priceRequestJson.put("date", "2020-06-14-16.00.00");
        HttpEntity<String> request = new HttpEntity<>(priceRequestJson.toString(), headers);

        ResponseEntity<PriceResponse> pricesResponse = restTemplate.postForEntity("/prices",
                request, PriceResponse.class);

        assertThat(pricesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(pricesResponse.getBody().getItems().get(0).getPriceList(), 2L);
    }

    @Test
    public void test_3() throws Exception {
        priceRequestJson.put("date", "2020-06-14-21.00.00");
        HttpEntity<String> request = new HttpEntity<>(priceRequestJson.toString(), headers);

        ResponseEntity<PriceResponse> pricesResponse = restTemplate.postForEntity("/prices",
                request, PriceResponse.class);

        assertThat(pricesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(pricesResponse.getBody().getItems().get(0).getPriceList(), 1L);
    }

    @Test
    public void test_4() throws Exception {
        priceRequestJson.put("date", "2020-06-15-10.00.00");
        HttpEntity<String> request = new HttpEntity<>(priceRequestJson.toString(), headers);

        ResponseEntity<PriceResponse> pricesResponse = restTemplate.postForEntity("/prices",
                request, PriceResponse.class);

        assertThat(pricesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(pricesResponse.getBody().getItems().get(0).getPriceList(), 3L);
    }

    @Test
    public void test_5() throws Exception {
        priceRequestJson.put("date", "2020-06-16-21.00.00");
        HttpEntity<String> request = new HttpEntity<>(priceRequestJson.toString(), headers);

        ResponseEntity<PriceResponse> pricesResponse = restTemplate.postForEntity("/prices",
                request, PriceResponse.class);

        assertThat(pricesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(pricesResponse.getBody().getItems().get(0).getPriceList(), 4L);
    }

}
