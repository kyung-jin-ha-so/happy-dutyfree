package com.its.happy.controller;

import com.its.happy.common.PagingConst;
import com.its.happy.dto.CouponDTO;
import com.its.happy.dto.CouponMemberDTO;
import com.its.happy.dto.EventDTO;
import com.its.happy.service.CouponService;
import com.its.happy.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;
    private final CouponService couponService;
    //이벤트 저장페이지 이동
    @GetMapping("/saveForm")
    public String saveForm(){
        return "/boardPages/save";
    }

    //이벤트 저장
    @PostMapping("/save")
    public String save(@ModelAttribute EventDTO eventDTO, @RequestParam("eventFile") List<MultipartFile> multipartFileList, @ModelAttribute
                       CouponDTO couponDTO) throws IOException {
        System.out.println("eventDTO = " + eventDTO + ", multipartFileList = " + multipartFileList + ", couponDTO = " + couponDTO);
        Long savedId = eventService.save(eventDTO, couponDTO);
        eventService.fileSave(savedId, multipartFileList);
        return "index";
    }

    //이벤트 페이징목록
    @GetMapping
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model){
        Page<EventDTO> eventList = eventService.paging(pageable);
        model.addAttribute("eventList", eventList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT)))-1) * PagingConst.BLOCK_LIMIT+1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < eventList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : eventList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        for (EventDTO e : eventList) {
            System.out.println("for문 동작");
            System.out.println(e);
        }
        return "/boardPages/eventList";
    }

    //이벤트 상세조회
    @GetMapping("/{eventId}")
    public String findById(@PathVariable Long eventId, Model model){
        EventDTO eventDTO = eventService.findById(eventId);
        model.addAttribute("event", eventDTO);
        return "/boardPages/detail";
    }
    //이벤트 수정페이지 요청
    @GetMapping("/update/{eventId}")
    public String update(@PathVariable Long eventId, Model model){
        EventDTO eventDTO = eventService.findById(eventId);
        model.addAttribute("event", eventDTO);
        return "/boardPages/update";
    }
    //이벤트 수정
    @PostMapping("/update")
    public String update(@ModelAttribute EventDTO eventDTO, @ModelAttribute CouponDTO couponDTO){
        eventService.update(eventDTO, couponDTO);
        System.out.println("eventDTO = " + eventDTO);
        return "redirect:/event/"+eventDTO.getEventId();
    }
    //이벤트 삭제
    @GetMapping("/delete/{eventId}")
    public String delete(@PathVariable Long eventId){
        eventService.deleteById(eventId);
        return "redirect:/event";
    }
    //이벤트 검색
    @GetMapping("/search")
    public String search(@RequestParam("q") String q, Model model){
        List<EventDTO> searchList = eventService.search(q);
        model.addAttribute("eventList", searchList);
        return "boardPages/eventList";
    }
}
