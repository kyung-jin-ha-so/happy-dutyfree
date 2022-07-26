package com.its.happy.entity;

import com.its.happy.dto.CartDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cart_table")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @Column(nullable = false)
    private int cartQty;

    // 장바구니(n)가 상품(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    // 장바구니(n)가 회원(1)을 참조함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    public static CartEntity toCartEntity(ProductEntity productEntity, MemberEntity memberEntity) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setCartQty(1);
        cartEntity.setProductEntity(productEntity);
        cartEntity.setMemberEntity(memberEntity);
        return cartEntity;

    }

    public static CartEntity toUpdateCart(CartDTO cartDTO) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setCartId(cartDTO.getCartId());
        cartEntity.setCartQty(cartDTO.getCartQty());
        return cartEntity;
    }
}
