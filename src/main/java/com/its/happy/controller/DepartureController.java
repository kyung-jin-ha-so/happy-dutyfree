package com.its.happy.controller;

import com.its.happy.api.ApiExplorer;
import com.its.happy.dto.DepartureDTO;
import com.its.happy.dto.FlightDTO;
import com.its.happy.service.DepartureService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/departure")
@RequiredArgsConstructor
public class DepartureController {
    private final DepartureService departureService;

    @GetMapping("/")
    public String findAll(Model model, HttpSession session) {
        Long loginId = (Long) session.getAttribute("loginId");
        List<DepartureDTO> departureDTOList = departureService.findAllByLoginId(loginId);
        model.addAttribute("departureList", departureDTOList);
        return "departurePages/list";
    }

    @GetMapping("/save-form")
    public String saveForm() {
        return "/departurePages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute DepartureDTO departureDTO, HttpSession session) {
        Long loginId = (Long) session.getAttribute("loginId");
        System.out.println("departureDTO = " + departureDTO);
        departureService.save(departureDTO, loginId);
        return "redirect:/member/mypage";
    }

    @GetMapping("/update-form/{id}")
    public String saveForm(@PathVariable Long id, Model model) {
        DepartureDTO departureDTO = departureService.findById(id);
        model.addAttribute("departure", departureDTO);
        return "/departurePages/update";
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        departureService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findFlightList")
    public @ResponseBody List<FlightDTO> findFlightList(@ModelAttribute FlightDTO flightDTO) throws JSONException, IOException {
        System.out.println("DepartureController.findFlightList");
        System.out.println("flightDTO = " + flightDTO);
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
            System.out.println("flightDTO1 = " + flightDTO1);
            flightDTOList.add(flightDTO1);
        }
        System.out.println("flightDTOList = " + flightDTOList);
        for (int i = 0; i < flightDTOList.size(); i++) {
            System.out.println(flightDTOList.get(i));
        }
        return flightDTOList;
    }

}
