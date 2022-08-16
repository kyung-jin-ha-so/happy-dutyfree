package com.its.happy.api;


import com.its.happy.dto.FlightDTO;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class ApiExplorer {
    public static String getFlight(FlightDTO flightDTO) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.airport.co.kr/service/rest/FlightScheduleList/getIflightScheduleList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=nLzuj5x1fefaIpwYrFwshTQfLrE28oSxxolY7w0e1J8mAUnAz8XESaS022qnqUW2RHnzr4bi4kM4aIVVnHlNRQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("schDate", "UTF-8") + "=" + URLEncoder.encode(flightDTO.getDepartureDate(), "UTF-8")); /*검색일자*/
        urlBuilder.append("&" + URLEncoder.encode("schArrvCityCode", "UTF-8") + "=" + URLEncoder.encode(flightDTO.getCityCode(), "UTF-8")); /*도착 도시 코드*/
        urlBuilder.append("&" + URLEncoder.encode("schAirLine", "UTF-8") + "=" + URLEncoder.encode(flightDTO.getAirline(), "UTF-8")); /*항공편 코드*/
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());

        return sb.toString();
    }
}