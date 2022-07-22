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

//    @GetMapping("/findFlightList")
//    public @ResponseBody List<CommentDTO> save(@ModelAttribute CommentDTO commentDTO) {
//        commentService.save(commentDTO);
//        List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
//        return commentDTOList;
//    }

}
