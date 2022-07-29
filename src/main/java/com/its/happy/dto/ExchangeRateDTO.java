package com.its.happy.dto;

import com.its.happy.entity.ExchangeRateEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDTO {
    private Long exchangeRateId;
    private double exchangeRate;
    private String exchangeRateDate;

    public static ExchangeRateDTO toDto(ExchangeRateEntity exchangeRateEntity) {
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        exchangeRateDTO.setExchangeRateId(exchangeRateEntity.getExchangeRateId());
        double exchangeRate = Math.round( exchangeRateEntity.getExchangeRate() * 100) / 100.0;
        exchangeRateDTO.setExchangeRate(exchangeRate);
        exchangeRateDTO.setExchangeRateDate(exchangeRateEntity.getExchangeRateDate());
        return  exchangeRateDTO;
    }
}
