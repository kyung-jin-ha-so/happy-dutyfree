package com.its.happy;

import com.its.happy.dto.CategoryDTO;
import com.its.happy.dto.ProductDTO;
import com.its.happy.entity.CategoryEntity;
import com.its.happy.entity.ProductEntity;
import com.its.happy.repository.CartRepository;
import com.its.happy.repository.CategoryRepository;
import com.its.happy.repository.ProductRepository;
import com.its.happy.service.CartService;
import com.its.happy.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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

    public ProductDTO newProduct(int i) {
        ProductDTO productDTO = new ProductDTO("임시상품" + i, 5000 + i, 4000 + i);
        return productDTO;
    }

    @Test
    @DisplayName("테스트용 카테고리 저장")
    public void categorySaveTest() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("임시카테고리");
        CategoryEntity categoryEntity = CategoryEntity.toSaveEntity(categoryDTO);
        categoryRepository.save(categoryEntity);
    }
    @Test
    @DisplayName("테스트용 상품 저장")
    public void productSaveTest() {
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(1L);
        CategoryEntity categoryEntity = optionalCategoryEntity.get();
        IntStream.rangeClosed(1, 5).forEach(i -> {
            ProductDTO productDTO = newProduct(i);
            ProductEntity productEntity = ProductEntity.toSaveEntity(productDTO, categoryEntity);
        productRepository.save(productEntity);
        });
    }

    @Test
    @DisplayName("테스트용 장바구니 저장")
    public void cartSaveTest() {
        cartService.save(2L, 1L, 2);
    }
}
