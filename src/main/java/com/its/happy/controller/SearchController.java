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
