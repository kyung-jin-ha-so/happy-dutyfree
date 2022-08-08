package com.its.happy.controller;

import com.its.happy.dto.MemberDTO;
import com.its.happy.dto.SearchDTO;
import com.its.happy.service.MemberService;
import com.its.happy.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/search")
@Controller
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final MemberService memberService;


    @GetMapping("/find")
    public String searchFind(HttpSession session, Model model) {
//        Long loginId = (Long) session.getAttribute("loginId");
//        if (loginId == null) {
//            return "redirect:/member/login";
//        }
//        List<SearchDTO> searchList = searchService.findByMemberId(loginId);
//        model.addAttribute("searchList", searchList);
        return "/searchPages/list";
    }

    @PostMapping("/find")
    public @ResponseBody List<SearchDTO> searchFind(@RequestParam Long loginId) {
        List<SearchDTO> searchDTOList = searchService.findByMemberId(loginId);
        System.out.println("searchDTOList = " + searchDTOList);
        return searchDTOList;
    }

    @PostMapping("/delete")
    public @ResponseBody String searchDeleteById(@RequestParam("searchId") Long searchId) {
        searchService.deleteById(searchId);
        return "삭제 완료";
    }
}
