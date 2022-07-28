package com.its.happy.service;

import com.its.happy.common.PagingConst;
import com.its.happy.dto.CategoryDTO;
import com.its.happy.dto.LikeDTO;
import com.its.happy.dto.ProductDTO;
import com.its.happy.dto.ProductFilesDTO;
import com.its.happy.entity.*;
import com.its.happy.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Member;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductFilesRepository productFilesRepository;
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;


    public Long save(ProductDTO productDTO, CategoryDTO categoryDTO) throws IOException {
        MultipartFile productThumbnailFile = productDTO.getProductThumbnailFile();
        String productThumbnail = productThumbnailFile.getOriginalFilename();
        productThumbnail = System.currentTimeMillis() + "-" + productThumbnail;
        String savePath = "C:\\happy_img\\" + productThumbnail;
        if (!productThumbnailFile.isEmpty()) {
            productThumbnailFile.transferTo(new File(savePath));
        }
        productDTO.setProductThumbnail(productThumbnail);
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryDTO.getCategoryId());
        if (optionalCategoryEntity.isPresent()) {
            CategoryEntity categoryEntity = optionalCategoryEntity.get();
            Long savedId = productRepository.save(ProductEntity.toSaveEntity(productDTO, categoryEntity)).getProductId();
            return savedId;
        }
        return null;
    }


    public void fileSave(Long savedId, List<MultipartFile> multipartFileList) throws IOException {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(savedId);
        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            ProductFilesDTO productFilesDTO = new ProductFilesDTO();
            for (MultipartFile file : multipartFileList) {
                String productFileName = file.getOriginalFilename();
                productFileName = System.currentTimeMillis() + "-" + productFileName;
                String savePath = "C:\\happy_img\\" + productFileName;
                if (!file.isEmpty()) {
                    file.transferTo(new File(savePath));
                    productFilesDTO.setProductFileName(productFileName);
                    productFilesRepository.save(ProductFilesEntity.toSaveEntity(productFilesDTO, productEntity));
                }
            }
        }
    }

    public ProductDTO findById(Long productId) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);
        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            return ProductDTO.toDTO(productEntity);
        }
        return null;
    }

    public Page<ProductDTO> findAll(Pageable pageable) {
        int page = pageReturn(pageable);
        Page<ProductEntity> productEntities = productRepository.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "productId")));
        return pageEntityToDTO(productEntities);
    }

    public Page<ProductDTO> findByCategory(Pageable pageable, Long categoryId) {
        int page = pageReturn(pageable);
        Page<ProductEntity> productEntities = productRepository.findByCategoryEntityCategoryId(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "productId")), categoryId);
        return pageEntityToDTO(productEntities);
    }

    public Page<ProductDTO> findByHighPrice(Pageable pageable, Long categoryId) {
        int page = pageReturn(pageable);
        Page<ProductEntity> productEntities = productRepository.findByCategoryEntityCategoryId(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "productPrice")), categoryId);
        return pageEntityToDTO(productEntities);
    }

    public Page<ProductDTO> findByLowPrice(Pageable pageable, Long categoryId) {
        int page = pageReturn(pageable);
        Page<ProductEntity> productEntities = productRepository.findByCategoryEntityCategoryId(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.ASC, "productPrice")), categoryId);
        return pageEntityToDTO(productEntities);
    }

    public Page<ProductDTO> findByStar(Pageable pageable, Long categoryId) {
        int page = pageReturn(pageable);
        Page<ProductEntity> productEntities = productRepository.findByCategoryEntityCategoryId(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "productStar")), categoryId);
        return pageEntityToDTO(productEntities);
    }

    private Page<ProductDTO> pageEntityToDTO(Page<ProductEntity> productEntities) {
        Page<ProductDTO> productList = productEntities.map(product -> new ProductDTO(
                product.getProductId(),
                product.getProductName(),
                product.getProductOriginalPrice(),
                product.getProductDiscount(),
                product.getProductPrice(),
                product.getProductStar(),
                product.getProductThumbnail(),
                product.getProductQuantity(),
                product.getProductBrand(),
                product.getProductStatus()
        ));
        return productList;
    }

    private int pageReturn(Pageable pageable) {
        int page = pageable.getPageNumber();
        page = (page == 1) ? 0 : (page - 1);
        return page;
    }

    public void statusClose(Long productId) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);
        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            productEntity.setProductStatus("판매중지");
            productRepository.save(productEntity);
        }
    }

    public void statusOpen(Long productId) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);
        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            productEntity.setProductStatus("판매중");
            productRepository.save(productEntity);
        }
    }

    public void changeQuantity(ProductDTO productDTO) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productDTO.getProductId());
        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            if (productEntity.getProductQuantity() == 0 && productEntity.getProductQuantity() < productDTO.getProductQuantity()) {
                productEntity.setProductStatus("판매중");
            }
            productEntity.setProductQuantity(productDTO.getProductQuantity());
            if (productDTO.getProductQuantity() == 0) {
                productEntity.setProductStatus("품절");
            }
            productRepository.save(productEntity);
        }
    }

    public Long update(ProductDTO productDTO, CategoryDTO categoryDTO) throws IOException {
        MultipartFile productThumbnailFile = productDTO.getProductThumbnailFile();
        String productThumbnail = productThumbnailFile.getOriginalFilename();
        productThumbnail = System.currentTimeMillis() + "-" + productThumbnail;
        String savePath = "C:\\happy_img\\" + productThumbnail;
        if (!productThumbnailFile.isEmpty()) {
            productThumbnailFile.transferTo(new File(savePath));
        } else {
            productThumbnail = productRepository.findById(productDTO.getProductId()).get().getProductThumbnail();
        }
        productDTO.setProductThumbnail(productThumbnail);
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryDTO.getCategoryId());
        if (optionalCategoryEntity.isPresent()) {
            CategoryEntity categoryEntity = optionalCategoryEntity.get();
            Long savedId = productRepository.save(ProductEntity.toUpdateEntity(productDTO, categoryEntity)).getProductId();
            return savedId;
        }
        return null;
    }

    public Page<ProductDTO> findSearch(Pageable pageable, String q) {
        int page = pageReturn(pageable);
        Page<ProductEntity> productEntities = productRepository.findByProductNameContaining(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "productId")), q);
        return pageEntityToDTO(productEntities);
    }

    public String like(Long productId, Long memberId) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        if (optionalMemberEntity.isPresent()) {
            if (optionalProductEntity.isPresent()) {
                ProductEntity productEntity = optionalProductEntity.get();
                MemberEntity memberEntity = optionalMemberEntity.get();
                Long save = likeRepository.save(LikeEntity.toLike(productEntity, memberEntity)).getLikeId();
                System.out.println("save = " + save);
                if (save != null) {
                    return "ok";
                } else {
                    return "no";
                }
            }
        }
        return null;
    }
    public LikeDTO findByLike(Long productId, Long memberId) {
        Optional<LikeEntity> optionalLikeEntity = likeRepository.findByProductEntity_ProductIdAndMemberEntity_MemberId(productId, memberId);
        if(optionalLikeEntity.isPresent()){
            return LikeDTO.toLikeDTO(optionalLikeEntity.get());
        } else {
            return null;
        }
    }
    @Transactional
    public void deleteById(Long memberId, Long productId) {
        likeRepository.deleteByMemberEntity_MemberIdAndProductEntity_ProductId(memberId, productId);
    }

    public List<LikeDTO> findByLikeList(Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        List<LikeDTO> likeDTOList = new ArrayList<>();
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            List<LikeEntity> likeEntityList = memberEntity.getLikeEntityList();
            for(LikeEntity likeEntity : likeEntityList){
                likeDTOList.add(LikeDTO.toLikeDTO(likeEntity));
            }
        }
        return likeDTOList;
    }

}


