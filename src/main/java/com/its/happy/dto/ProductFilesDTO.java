package com.its.happy.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class ProductFilesDTO {

    private Long productFileId;
    private MultipartFile productFile;
    private String productFileName;

}
