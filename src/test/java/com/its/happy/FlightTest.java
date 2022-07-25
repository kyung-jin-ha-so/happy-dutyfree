package com.its.happy;

import com.google.gson.Gson;
import com.its.happy.api.ApiExplorer;
import com.its.happy.dto.FlightDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@SpringBootTest
public class FlightTest {

    @Test
    @DisplayName("항공API 테스트")
    @Transactional
    @Rollback(value = false)
    public void flightApiTest() throws IOException, JSONException {
        String result = ApiExplorer.getFlight();
//        Gson gson = new Gson();
//        FlightDTO flightDTO = gson.fromJson(result, FlightDTO.class);
//        System.out.println(flightDTO.getResponse().getBody().getItems().getItem().get(0).getInternationalTime());

        // 가장 큰 JSONObject를 가져옵니다.
        JSONObject jObject = new JSONObject(result);

        // 배열을 가져옵니다.
        JSONObject  jObject2 = jObject.getJSONObject("response");
        JSONObject  jObject3 = jObject2.getJSONObject("body");
        JSONObject  jObject4 = jObject3.getJSONObject("items");
        JSONArray  jArray  = jObject4.getJSONArray("item");
        System.out.println("jArray:" + jArray);

        // 배열의 모든 아이템을 출력합니다.
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject obj = jArray.getJSONObject(i);
            String airlineKorean = obj.getString("airlineKorean");
            String airportCode = obj.getString("airportCode");
            String airport = obj.getString("airport");
            String cityCode = obj.getString("cityCode");
            String city = obj.getString("city");
            String internationalNum = obj.getString("internationalNum");
            String internationalTime = obj.getString("internationalTime");
            System.out.println("airlineKorean(" + i + "): " + airlineKorean);
            System.out.println("airportCode(" + i + "): " + airportCode);
            System.out.println("airport(" + i + "): " + airport);
            System.out.println("cityCode(" + i + "): " + cityCode);
            System.out.println("city(" + i + "): " + city);
            System.out.println("internationalNum(" + i + "): " + internationalNum);
            System.out.println("internationalTime(" + i + "): " + internationalTime);
            System.out.println();
        }

    }
}
