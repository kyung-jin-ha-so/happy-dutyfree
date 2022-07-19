package com.its.happy.controller;

import com.its.happy.dto.DepartureDTO;
import com.its.happy.service.DepartureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/departure")
@RequiredArgsConstructor
public class DepartureController {
    private final DepartureService departureService;

    @GetMapping("/save-form/{id}")
    public String saveForm(@PathVariable Long id, Model model) {
        DepartureDTO departureDTO = departureService.findById(id);
        model.addAttribute("departure", departureDTO);
        return "/departurePages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute DepartureDTO departureDTO, HttpSession session) {
        Long loginId = (Long) session.getAttribute("loginId");
        departureService.save(departureDTO, loginId);
        return "redirect:/member/mypage";
    }

    @GetMapping("/")
    public String findAll(Model model, HttpSession session) {
        Long loginId = (Long) session.getAttribute("loginId");
        List<DepartureDTO> departureDTOList = departureService.findAllByLoginId(loginId);
        model.addAttribute("departureList", departureDTOList);
        return "departurePages/list";
    }

//    @GetMapping("/update-form")
//    public String updateForm(Model model, HttpSession session) {
//        MemberDTO memberDTO = memberService.findById((Long) session.getAttribute("loginId"));
//        model.addAttribute("member", memberDTO);
//        return "/memberPages/update";
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity update(@RequestBody MemberDTO memberDTO) throws IOException {
//        System.out.println("MemberController.update");
//        System.out.println("memberDTO = " + memberDTO);
//        memberService.update(memberDTO);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @GetMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        departureService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
