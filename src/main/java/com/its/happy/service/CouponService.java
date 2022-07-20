package com.its.happy.service;

import com.its.happy.dto.CouponDTO;
import com.its.happy.entity.CouponEntity;
import com.its.happy.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public List<CouponDTO> findAll() {
        List<CouponEntity>entityList = couponRepository.findAll();
        List<CouponDTO>dtoList = new ArrayList<>();
        for(CouponEntity couponEntity : entityList){
            dtoList.add(CouponDTO.toCouponDTO(couponEntity));
        }
        return dtoList;
    }
}
