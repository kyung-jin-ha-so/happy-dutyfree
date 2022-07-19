package com.its.happy.entity;

import com.its.happy.dto.CategoryDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "category_table")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    public Long categoryId;

    @Column(name = "category_name", length = 30, nullable = false)
    public String categoryName;

    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<ProductEntity> productEntityList = new ArrayList<>();

    @PreRemove
    private void preRemove() {
        productEntityList.forEach(product -> product.setCategoryEntity(null));
    }

    public static CategoryEntity toSaveEntity(CategoryDTO categoryDTO){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName(categoryDTO.getCategoryName());
        return categoryEntity;
    }
}
