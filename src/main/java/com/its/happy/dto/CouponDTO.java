package com.its.happy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {
    private Long couponId;
    private String couponName;
    private Long couponValue;
    private Long couponMinimumValue;
    private String couponThumbnail;
    private MultipartFile couponThumbnailFile;
}
