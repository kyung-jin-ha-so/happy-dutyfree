package com.its.happy;

import com.its.happy.dto.CategoryDTO;
import com.its.happy.dto.CouponDTO;
import com.its.happy.dto.CouponMemberDTO;
import com.its.happy.dto.ProductDTO;
import com.its.happy.entity.CategoryEntity;
import com.its.happy.entity.ProductEntity;
import com.its.happy.repository.CartRepository;
import com.its.happy.repository.CategoryRepository;
import com.its.happy.repository.CouponRepository;
import com.its.happy.repository.ProductRepository;
import com.its.happy.service.CartService;
import com.its.happy.service.CouponService;
import com.its.happy.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.IntStream;


@SpringBootTest
public class OrderTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CartService cartService;

    @Autowired
    private CouponService couponService;

//    public ProductDTO newProduct(int i) {
//        ProductDTO productDTO = new ProductDTO("임시상품" + i, 10, 50L, 5);
//        return productDTO;
//    }

    @Test
    @DisplayName("테스트용 카테고리 저장")
    public void categorySaveTest() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("임시카테고리");
        CategoryEntity categoryEntity = CategoryEntity.toSaveEntity(categoryDTO);
        categoryRepository.save(categoryEntity);
    }

//    @Test
//    @DisplayName("테스트용 상품 저장")
//    public void productSaveTest() {
//        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(1L);
//        CategoryEntity categoryEntity = optionalCategoryEntity.get();
//        IntStream.rangeClosed(1, 5).forEach(i -> {
//            ProductDTO productDTO = newProduct(i);
//            ProductEntity productEntity = ProductEntity.toSaveEntity(productDTO, categoryEntity);
//            productRepository.save(productEntity);
//        });
//    }

    @Test
    @DisplayName("테스트용 장바구니 저장")
    public void cartSaveTest() {
        cartService.save(1L, 1L, 2);
        cartService.save(2L, 1L, 3);
    }

    @Test
    @DisplayName("주문 테스트용 정보 저장")
    public void orderTest() {
        // 카테고리 저장
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("임시카테고리");
        CategoryEntity categoryEntity = CategoryEntity.toSaveEntity(categoryDTO);
        categoryRepository.save(categoryEntity);

        // 테스트 상품 저장
        ProductDTO productDTO = new ProductDTO("임시상품1", 10, 50L, 5, 5L);
        ProductDTO productDTO2 = new ProductDTO("임시상품2", 20, 75L, 5, 10L);
        ProductEntity productEntity = ProductEntity.toSaveEntity(productDTO, categoryEntity);
        ProductEntity productEntity2 = ProductEntity.toSaveEntity(productDTO2, categoryEntity);
        productRepository.save(productEntity);
        productRepository.save(productEntity2);

        // 장바구니 저장
        cartService.save(1L, 1L, 3);
        cartService.save(2L, 1L, 2);
    }

//    @Test
//    @DisplayName("주문 테스트용 정보 저장")
//    public void orderTest2() throws IOException {
//        CouponDTO couponDTO = new CouponDTO("만원쿠폰", 10000L, 1000L, "tt");
//        CouponDTO couponDTO2 = new CouponDTO("오처넌쿠폰", 5000L, 1000L, "ts");
//        Long couponId = couponService.save(couponDTO);
//        Long couponId2 = couponService.save(couponDTO2);
//        couponService.issueCoupon(couponId, 1L);
//        couponService.issueCoupon(couponId2, 1L);
//    }
}
