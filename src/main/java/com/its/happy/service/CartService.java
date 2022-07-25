package com.its.happy.service;

import com.its.happy.dto.CartDTO;
import com.its.happy.dto.LikeDTO;
import com.its.happy.entity.CartEntity;
import com.its.happy.entity.LikeEntity;
import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.ProductEntity;
import com.its.happy.repository.CartRepository;
import com.its.happy.repository.MemberRepository;
import com.its.happy.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public String save(Long productId, Long memberId) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            MemberEntity memberEntity = optionalMemberEntity.get();
            Long save = cartRepository.save(CartEntity.toCartEntity(productEntity, memberEntity)).getCartId();
            System.out.println("save = " + save);
            if (save != null) {
                return "ok";
            } else {
                return "no";
            }
        }
        return null;
    }

    public CartDTO findByCart(Long productId, Long memberId) {
        Optional<CartEntity> optionalCartEntity = cartRepository.findByProductEntity_ProductIdAndMemberEntity_MemberId(productId, memberId);
        if (optionalCartEntity.isPresent()) {
            return CartDTO.toCartDTO(optionalCartEntity.get());
        } else {
            return null;
        }
    }

    @Transactional
    public void update(Long productId, Long memberId) {
        System.out.println("CartService.update");
        cartRepository.cartQty(productId, memberId);
    }

    public List<CartDTO> findById(Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        List<CartDTO> cartDTOList = new ArrayList<>();
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            List<CartEntity> cartEntityList = memberEntity.getCartEntityList();
            for(CartEntity cartEntity : cartEntityList){
                cartDTOList.add(CartDTO.toCartDTO(cartEntity));
            }
        }
        return cartDTOList;
    }
}

