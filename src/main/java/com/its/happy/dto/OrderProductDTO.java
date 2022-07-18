package com.its.happy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDTO {
    private Long orderProductId;
    private int orderQty;
    private double orderWon;
    private double orderDollar;
}
