package com.inditex.msprices.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Price {
    @Id
    private Long priceId;
    @JsonFormat(pattern = "yyyy-MM-dd-HH.mm.ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd-HH.mm.ss")
    private LocalDateTime endDate;
    private Long productId;
    private Integer priority;
    private BigDecimal price;
    private String currency;

    @ManyToOne(fetch= FetchType.LAZY)
    private Brand brand;
}
