package com.its.happy.service;

import com.its.happy.entity.ExchangeRateEntity;
import com.its.happy.repository.ExchangeRateRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    public void save(double exchange) {
        ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity();
        exchangeRateEntity.setExchangeRate(exchange);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println(formatter.format(date));
        exchangeRateEntity.setExchangeRateDate(formatter.format(date));
        exchangeRateRepository.save(exchangeRateEntity);
    }
}
