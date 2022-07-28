package com.its.happy.dto;

import com.its.happy.entity.CouponEntity;
import com.its.happy.entity.CouponMemberEntity;
import com.its.happy.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponMemberDTO {
    private Long couponMemberId;
    private String couponStatus;
    private CouponEntity couponEntity;
    private MemberEntity memberEntity;

    public static CouponMemberDTO toSaveDTO(CouponMemberEntity couponMemberEntity) {
        CouponMemberDTO couponMemberDTO = new CouponMemberDTO();
        couponMemberDTO.setCouponMemberId(couponMemberEntity.getCouponMemberId());
        couponMemberDTO.setCouponStatus(couponMemberEntity.getCouponStatus());
        couponMemberDTO.setCouponEntity(couponMemberEntity.getCouponEntity());
        couponMemberDTO.setMemberEntity(couponMemberDTO.getMemberEntity());
        return couponMemberDTO;
    }

    public static CouponMemberDTO toCouponMemberDTO(CouponMemberEntity couponMemberEntity) {
        CouponMemberDTO couponMemberDTO = new CouponMemberDTO();
        couponMemberDTO.setCouponMemberId(couponMemberEntity.getCouponMemberId());
        couponMemberDTO.setCouponEntity(couponMemberEntity.getCouponEntity());
        couponMemberDTO.setMemberEntity(couponMemberEntity.getMemberEntity());
        return couponMemberDTO;
    }
}
