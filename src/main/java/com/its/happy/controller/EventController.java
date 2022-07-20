package com.its.happy.controller;

import com.its.happy.dto.CouponDTO;
import com.its.happy.dto.EventDTO;
import com.its.happy.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    @GetMapping("/saveForm")
    public String saveForm(){
        return "/boardPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute EventDTO eventDTO, @RequestParam("eventFile") List<MultipartFile> multipartFileList, @ModelAttribute
                       CouponDTO couponDTO) throws IOException {
        System.out.println("eventDTO = " + eventDTO + ", multipartFileList = " + multipartFileList + ", couponDTO = " + couponDTO);
        Long savedId = eventService.save(eventDTO, couponDTO);
        eventService.fileSave(savedId, multipartFileList);
        return "index";
    }
//    @PostMapping("/save")
//    public String save(@ModelAttribute ProductDTO productDTO, @RequestParam("productFile") List<MultipartFile> multipartFileList,
//                       @ModelAttribute CategoryDTO categoryDTO) throws IOException {
//        System.out.println("productDTO = " + productDTO + ", multipartFileList = " + multipartFileList + ", categoryDTO = " + categoryDTO);
//        Long savedId = productService.save(productDTO, categoryDTO);
//        productService.fileSave(savedId, multipartFileList);
//        return "index";
//    }

}
