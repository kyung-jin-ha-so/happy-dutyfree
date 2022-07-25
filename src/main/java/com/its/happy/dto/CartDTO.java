package com.its.happy.dto;

import com.its.happy.entity.CartEntity;
import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.ProductEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CartDTO {
    private Long cartId;
    private int cartQty;
    private Long productId;
    private Long memberId;
    private String productName;
    private double productOriginalPrice;;
    private Long productDiscount;
    private double productPrice;
    private String productThumbnail;


    public static CartDTO toCartDTO(CartEntity cartEntity) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cartEntity.getCartId());
        cartDTO.setCartQty(cartEntity.getCartQty());
        cartDTO.setProductId(cartEntity.getProductEntity().getProductId());
        cartDTO.setMemberId(cartEntity.getMemberEntity().getMemberId());
        cartDTO.setProductName(cartEntity.getProductEntity().getProductName());
        cartDTO.setProductOriginalPrice(cartEntity.getProductEntity().getProductOriginalPrice());
        cartDTO.setProductDiscount(cartEntity.getProductEntity().getProductDiscount());
        cartDTO.setProductThumbnail(cartEntity.getProductEntity().getProductThumbnail());
        cartDTO.setProductPrice(cartEntity.getProductEntity().getProductPrice());
        return cartDTO;
    }
}
