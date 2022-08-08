package com.its.happy.dto;

import com.its.happy.entity.CouponEntity;
import com.its.happy.entity.CouponMemberEntity;
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

    private CouponMemberEntity couponMemberEntity;

    public CouponDTO(String couponName, Long couponValue, Long couponMinimumValue, String couponThumbnail) {
        this.couponName = couponName;
        this.couponValue = couponValue;
        this.couponMinimumValue = couponMinimumValue;
        this.couponThumbnail = couponThumbnail;
    }

    public static CouponDTO toCouponDTO(CouponEntity couponEntity) {
        CouponDTO couponDTO = new CouponDTO();
        couponDTO.setCouponId(couponEntity.getCouponId());
        couponDTO.setCouponName(couponEntity.getCouponName());
        couponDTO.setCouponValue(couponEntity.getCouponValue());
        couponDTO.setCouponMinimumValue(couponEntity.getCouponMinimumValue());
        couponDTO.setCouponThumbnail(couponEntity.getCouponThumbnail());
        return couponDTO;
    }
}
