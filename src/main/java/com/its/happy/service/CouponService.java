package com.its.happy.service;

import com.its.happy.dto.CouponDTO;
import com.its.happy.entity.CouponEntity;
import com.its.happy.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;

    public Long save(CouponDTO couponDTO) throws IOException {
        System.out.println("couponDTO = " + couponDTO);
        MultipartFile couponThumbnailFile = couponDTO.getCouponThumbnailFile();
        String couponThumbnail = couponThumbnailFile.getOriginalFilename();
        couponThumbnail = System.currentTimeMillis() + "_" + couponThumbnail;
        String savePath = "C:\\happy_img\\" + couponThumbnail;
        if (!couponThumbnailFile.isEmpty()) {
            couponThumbnailFile.transferTo(new File(savePath));
        }
        couponDTO.setCouponThumbnail(couponThumbnail);
        return couponRepository.save(CouponEntity.toCoupon(couponDTO)).getCouponId();
    }
}
