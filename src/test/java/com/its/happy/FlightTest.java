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
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class FlightTest {

    @Test
    @DisplayName("항공API 테스트")
    @Transactional
    @Rollback(value = false)
    public void flightApiTest() throws IOException, JSONException {
        FlightDTO flightDTO = new FlightDTO("20220726", "ICN", "KE");
        String result = ApiExplorer.getFlight(flightDTO);
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
        @Test
        @DisplayName("항공API 리스트 테스트")
        @Transactional
        @Rollback(value = false)
        public void flightListApiTest() throws IOException, JSONException {
            FlightDTO flightDTO = new FlightDTO("20220924", "ICN", "KE");
            String flight = ApiExplorer.getFlight(flightDTO);
            JSONObject jObject = new JSONObject(flight);
            JSONObject  jObject2 = jObject.getJSONObject("response");
            JSONObject  jObject3 = jObject2.getJSONObject("body");
            JSONObject  jObject4 = jObject3.getJSONObject("items");
            JSONArray jArray  = jObject4.getJSONArray("item");
            List<FlightDTO> flightDTOList = new ArrayList<>();
            for (int i = 0; i < jArray.length(); i++) {
                FlightDTO flightDTO1 = new FlightDTO();
                JSONObject obj = jArray.getJSONObject(i);
                flightDTO1.setAirport(obj.getString("airport"));
                flightDTO1.setAirportCode(obj.getString("airportCode"));
                flightDTO1.setInternationalNum(obj.getString("internationalNum"));
                flightDTO1.setInternationalTime(obj.getString("internationalTime"));
                flightDTO1.setAirlineKorean(obj.getString("airlineKorean"));
                System.out.println("flightDTO = " + flightDTO1);
                flightDTOList.add(flightDTO1);
            }
            System.out.println("flightDTOList = " + flightDTOList);
            for (int i = 0; i < flightDTOList.size(); i++) {
                System.out.println(flightDTOList.get(i));
            }
        }
    }
