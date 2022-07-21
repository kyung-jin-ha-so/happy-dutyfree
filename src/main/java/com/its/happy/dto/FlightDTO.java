package com.its.happy.dto;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
class Body {

    private Items items;
    private Integer numOfRows;
    private Integer pageNo;
    private Integer totalCount;

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public Integer getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(Integer numOfRows) {
        this.numOfRows = numOfRows;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}

@Generated("jsonschema2pojo")
public class FlightDTO {

    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}

@Generated("jsonschema2pojo")
class Header {

    private String resultCode;
    private String resultMsg;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

}

@Generated("jsonschema2pojo")
class Item {

    private String airlineEnglish;
    private String airlineKorean;
    private String airport;
    private String airportCode;
    private String city;
    private String cityCode;
    private String flightPurpose;
    private String internationalEddate;
    private String internationalFri;
    private String internationalIoType;
    private String internationalMon;
    private String internationalNum;
    private String internationalSat;
    private String internationalStdate;
    private String internationalSun;
    private String internationalThu;
    private Integer internationalTime;
    private String internationalTue;
    private String internationalWed;

    public String getAirlineEnglish() {
        return airlineEnglish;
    }

    public void setAirlineEnglish(String airlineEnglish) {
        this.airlineEnglish = airlineEnglish;
    }

    public String getAirlineKorean() {
        return airlineKorean;
    }

    public void setAirlineKorean(String airlineKorean) {
        this.airlineKorean = airlineKorean;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getFlightPurpose() {
        return flightPurpose;
    }

    public void setFlightPurpose(String flightPurpose) {
        this.flightPurpose = flightPurpose;
    }

    public String getInternationalEddate() {
        return internationalEddate;
    }

    public void setInternationalEddate(String internationalEddate) {
        this.internationalEddate = internationalEddate;
    }

    public String getInternationalFri() {
        return internationalFri;
    }

    public void setInternationalFri(String internationalFri) {
        this.internationalFri = internationalFri;
    }

    public String getInternationalIoType() {
        return internationalIoType;
    }

    public void setInternationalIoType(String internationalIoType) {
        this.internationalIoType = internationalIoType;
    }

    public String getInternationalMon() {
        return internationalMon;
    }

    public void setInternationalMon(String internationalMon) {
        this.internationalMon = internationalMon;
    }

    public String getInternationalNum() {
        return internationalNum;
    }

    public void setInternationalNum(String internationalNum) {
        this.internationalNum = internationalNum;
    }

    public String getInternationalSat() {
        return internationalSat;
    }

    public void setInternationalSat(String internationalSat) {
        this.internationalSat = internationalSat;
    }

    public String getInternationalStdate() {
        return internationalStdate;
    }

    public void setInternationalStdate(String internationalStdate) {
        this.internationalStdate = internationalStdate;
    }

    public String getInternationalSun() {
        return internationalSun;
    }

    public void setInternationalSun(String internationalSun) {
        this.internationalSun = internationalSun;
    }

    public String getInternationalThu() {
        return internationalThu;
    }

    public void setInternationalThu(String internationalThu) {
        this.internationalThu = internationalThu;
    }

    public Integer getInternationalTime() {
        return internationalTime;
    }

    public void setInternationalTime(Integer internationalTime) {
        this.internationalTime = internationalTime;
    }

    public String getInternationalTue() {
        return internationalTue;
    }

    public void setInternationalTue(String internationalTue) {
        this.internationalTue = internationalTue;
    }

    public String getInternationalWed() {
        return internationalWed;
    }

    public void setInternationalWed(String internationalWed) {
        this.internationalWed = internationalWed;
    }

}

@Generated("jsonschema2pojo")
class Items {

    private List<Item> item = null;

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

}

@Generated("jsonschema2pojo")
class Response {

    private Header header;
    private Body body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

}