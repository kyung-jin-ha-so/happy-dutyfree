package com.its.happy.dto;

import com.its.happy.entity.CartEntity;
import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.ProductEntity;
import lombok.Data;

@Data
public class CartDTO {
    private Long cartId;
    private int cartQty;
    private Long productId;
    private Long memberId;

    public static CartDTO toCartDTO(CartEntity cartEntity) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cartEntity.getCartId());
        cartDTO.setCartQty(cartEntity.getCartQty());
        cartDTO.setProductId(cartEntity.getProductEntity().getProductId());
        cartDTO.setMemberId(cartEntity.getMemberEntity().getMemberId());
        return cartDTO;
    }
}
