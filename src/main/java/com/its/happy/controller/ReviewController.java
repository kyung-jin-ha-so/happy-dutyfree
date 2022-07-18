package com.its.happy.controller;

import com.its.happy.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

}
