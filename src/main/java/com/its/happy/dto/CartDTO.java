package com.its.happy.dto;

import lombok.Data;

@Data
public class CartDTO {
    private Long cartId;
    private Long productId;
    private Long memberId;
    private int cartQty;
}
