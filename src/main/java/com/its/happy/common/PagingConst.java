package com.its.happy.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("paging")
public class PagingConst {

    public static int PAGE_LIMIT = 2; // 한페이지에 보여줄 글 갯수
    public static final int BLOCK_LIMIT = 3; // 한화면에 보여줄 페이지 갯수

}
