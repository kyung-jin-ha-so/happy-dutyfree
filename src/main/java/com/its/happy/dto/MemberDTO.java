package com.its.happy.dto;

import com.its.happy.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberMobile;
    private String memberBirth;
    private String memberKakaoId;
    private String memberTier;
    private LocalDateTime memberCreatedTime;
    private List<PointEntity> pointEntityList;
    private PassportEntity passportEntity;
    private List<ReviewEntity> reviewEntityList;
    private List<SearchEntity> searchEntityList;
    private List<LikeEntity> likeEntityList;
    private List<CartEntity> cartEntityList;
    private List<OrderEntity> orderEntityList;
    private List<OrderDepartureEntity> orderDepartureEntityList;
    private List<CouponMemberEntity> couponMemberEntityList;
    private List<DepartureEntity> departureEntityList;


    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId(memberEntity.getMemberId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberMobile(memberEntity.getMemberMobile());
        memberDTO.setMemberBirth(memberEntity.getMemberBirth());
        memberDTO.setMemberKakaoId(memberEntity.getMemberKakaoId());
        memberDTO.setMemberTier(memberEntity.getMemberTier());
        memberDTO.setMemberCreatedTime(memberEntity.getCreatedTime());
        memberDTO.setPointEntityList(memberEntity.getPointEntityList());
        memberDTO.setPassportEntity(memberEntity.getPassportEntity());
        memberDTO.setReviewEntityList(memberEntity.getReviewEntityList());
        memberDTO.setSearchEntityList(memberEntity.getSearchEntityList());
        memberDTO.setLikeEntityList(memberEntity.getLikeEntityList());
        memberDTO.setCartEntityList(memberEntity.getCartEntityList());
        memberDTO.setOrderEntityList(memberEntity.getOrderEntityList());
        memberDTO.setOrderDepartureEntityList(memberEntity.getOrderDepartureEntityList());
        memberDTO.setCouponMemberEntityList(memberEntity.getCouponMemberEntityList());
        memberDTO.setDepartureEntityList(memberEntity.getDepartureEntityList());
        return memberDTO;
    }

}
