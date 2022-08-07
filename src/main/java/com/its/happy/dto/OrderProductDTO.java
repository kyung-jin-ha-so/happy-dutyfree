package com.its.happy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDTO {
    private Long orderProductId;
    private double productOriginalPrice;
    private Long productDiscount;
    private int orderQty;
}
