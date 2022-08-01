package com.its.happy.service;

import com.its.happy.dto.*;
import com.its.happy.entity.*;
import com.its.happy.repository.CouponMemberRepository;
import com.its.happy.repository.CouponRepository;
import com.its.happy.repository.EventRepository;
import com.its.happy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final CouponMemberRepository couponMemberRepository;
    private final EventRepository eventRepository;

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
        List<CouponEntity> entityList = couponRepository.findAll();
        List<CouponDTO> dtoList = new ArrayList<>();
        for (CouponEntity couponEntity : entityList) {
            dtoList.add(CouponDTO.toCouponDTO(couponEntity));
        }
        return dtoList;
    }

    public String issueCoupon(Long couponId, Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        Optional<CouponEntity> optionalCouponEntity = couponRepository.findById(couponId);
        if (optionalMemberEntity.isPresent()) {
            if (optionalCouponEntity.isPresent()) {
                MemberEntity memberEntity = optionalMemberEntity.get();
                CouponEntity couponEntity = optionalCouponEntity.get();
                Long save = couponMemberRepository.save(CouponMemberEntity.toCouponMember(memberEntity, couponEntity)).getCouponMemberId();
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

    public List<CouponMemberDTO> findAllIssueCoupon(Long couponId) {
        List<CouponMemberEntity> couponMemberEntityList = couponMemberRepository.findByCouponMemberId(couponId);
        List<CouponMemberDTO> couponMemberDTOList = new ArrayList<>();
        for (CouponMemberEntity couponMemberEntity : couponMemberEntityList) {
            couponMemberDTOList.add(CouponMemberDTO.toSaveDTO(couponMemberEntity));
        }
        return couponMemberDTOList;
    }

    public CouponMemberDTO findById(Long memberId, String couponName, Long couponId) {
        Optional<CouponMemberEntity> optionalCouponMemberEntity = couponMemberRepository.findByCouponEntity_CouponNameAndMemberEntity_MemberIdAndCouponEntity_CouponId(couponName, memberId, couponId);
        if (optionalCouponMemberEntity.isPresent()) {
            return CouponMemberDTO.toCouponMemberDTO(optionalCouponMemberEntity.get());
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteById(Long couponId) {
        couponRepository.deleteById(couponId);
    }

    public List<CouponMemberDTO> findByMyCoupon(Long memberId) {
        List<CouponMemberEntity> couponMemberEntityList = couponMemberRepository.findByMemberEntity_MemberId(memberId);
        List<CouponMemberDTO> couponMemberDTOList = new ArrayList<>();
        for (CouponMemberEntity couponMemberEntity : couponMemberEntityList) {
            couponMemberDTOList.add(CouponMemberDTO.toSaveDTO(couponMemberEntity));
        }
        return couponMemberDTOList;
    }

    public CouponDTO findByCouponId(Long couponId) {
        Optional<CouponEntity> optionalCouponEntity = couponRepository.findById(couponId);
        if (optionalCouponEntity.isPresent()) {
            return CouponDTO.toCouponDTO(optionalCouponEntity.get());
        } else {
            return null;
        }
    }
}
