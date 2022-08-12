package com.its.happy.service;

import com.its.happy.dto.CartDTO;
import com.its.happy.dto.OrderDTO;
import com.its.happy.entity.CartEntity;
import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.OrderEntity;
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
    public String save(Long productId, Long memberId, int cartQty) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            MemberEntity memberEntity = optionalMemberEntity.get();
            Long save = cartRepository.save(CartEntity.toCartEntity(cartQty, productEntity, memberEntity)).getCartId();
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
    public void update(Long productId, Long memberId, int cartQty) {
        System.out.println("CartService.update");
        cartRepository.cartQty(productId, memberId, cartQty);
    }

    public List<CartDTO> findById(Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        List<CartDTO> cartDTOList = new ArrayList<>();
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            List<CartEntity> cartEntityList = memberEntity.getCartEntityList();
            for (CartEntity cartEntity : cartEntityList) {
                cartDTOList.add(CartDTO.toCartDTO(cartEntity));
            }
        }
        return cartDTOList;
    }

    public void updateCartQty(CartDTO cartDTO) {
        Optional<CartEntity> optionalCartEntity = cartRepository.findById(cartDTO.getCartId());
        if (optionalCartEntity.isPresent()) {
            CartEntity cartEntity = optionalCartEntity.get();
            MemberEntity memberEntity = cartEntity.getMemberEntity();
            ProductEntity productEntity = cartEntity.getProductEntity();
            CartEntity cartEntity1 = cartEntity.toUpdateCart(cartDTO);
            cartEntity1.setMemberEntity(memberEntity);
            cartEntity1.setProductEntity(productEntity);
            cartRepository.save(cartEntity1);
        }
    }

    @Transactional
    public void deleteById(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public CartDTO save2(CartDTO cartDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(cartDTO.getMemberId());
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(cartDTO.getProductId());
        if (optionalMemberEntity.isPresent()) {
            if (optionalProductEntity.isPresent()) {
                MemberEntity memberEntity = optionalMemberEntity.get();
                ProductEntity productEntity = optionalProductEntity.get();
                return CartDTO.toCartDTO(cartRepository.save(CartEntity.toCartEntity2(cartDTO, memberEntity, productEntity)));
            }
        }
        return null;
    }

    public CartDTO findByProductIdMemberId(Long productId, Long memberId) {
        Optional<CartEntity> optionalCartEntity = cartRepository.findByProductEntity_ProductIdAndMemberEntity_MemberId(productId, memberId);
        if (optionalCartEntity.isPresent()) {
            CartEntity cartEntity = optionalCartEntity.get();
            return CartDTO.toCartDTO(cartEntity);
        }
        return null;
    }

    public List<CartDTO> findByMemberId(Long memberId) {
        List<CartEntity> cartEntityList = cartRepository.findByMemberEntity_MemberId(memberId);
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (CartEntity c :
                cartEntityList) {
            cartDTOList.add(CartDTO.toCartDTO(c));
        }
        return cartDTOList;
    }
}

