package com.its.happy.controller;
import com.its.happy.entity.ExchangeRateEntity;
import com.its.happy.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
@Component
@RequestMapping("/exchange")
@Controller
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    // 매일 오전 9시에 환율이 환율테이블로 날짜와 환율이 들어갈 예정
    // api Key가 있어야 실행됨

//    @Scheduled(cron = "0 0 9 * * ?", zone = "Asia/Seoul") // 매일 25분에 실행
//    public void cronRun() throws JSONException, IOException {
//        exchangeRate();
//    }
//
//    @GetMapping("/find")
//    public void exchangeRate() throws IOException, JSONException {
//        OkHttpClient client = new OkHttpClient().newBuilder().build();
//
//        Request request = new Request.Builder()
//                .url("https://api.apilayer.com/exchangerates_data/convert?to=KRW&from=USD&amount=1")
//                .addHeader("apikey", "")
//                //value에 api키가 들어갈 예정
//                .method("GET", null)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        String jsonData = response.body().string();
//        JSONObject Jobject = new JSONObject(jsonData);
//        String exchangeS = Jobject.getString("result");
//        double exchange = Double.parseDouble(exchangeS);
//        System.out.println("exchange = " + exchange);
//        exchangeRateService.save(exchange);
//    }
}
