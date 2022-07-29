package com.its.happy.service;

import com.its.happy.dto.ExchangeRateDTO;
import com.its.happy.entity.ExchangeRateEntity;
import com.its.happy.repository.ExchangeRateRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

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

    //해당 날짜 환율DTO 불러오는 기능
    public ExchangeRateDTO findByDate(){
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Optional<ExchangeRateEntity> optionalExchangeRateEntity = exchangeRateRepository.findByExchangeRateDateEquals(formatter.format(today));
        if(optionalExchangeRateEntity.isPresent()){
            ExchangeRateEntity exchangeRateEntity = optionalExchangeRateEntity.get();
            return ExchangeRateDTO.toDto(exchangeRateEntity);
        } return null;
    }
}
