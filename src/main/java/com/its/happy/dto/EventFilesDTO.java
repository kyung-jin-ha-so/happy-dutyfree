package com.its.happy.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EventFilesDTO {
    private Long eventFileId;
    private MultipartFile eventFile;
    private String eventFileName;
}
