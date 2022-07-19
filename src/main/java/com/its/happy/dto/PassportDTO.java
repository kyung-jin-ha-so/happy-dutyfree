package com.its.happy.dto;

import com.its.happy.entity.MemberEntity;
import com.its.happy.entity.PassportEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassportDTO {
    private Long passportId;
    private String passportNumber;
    private String passportName;
    private String passportDate;

    public PassportDTO(String passportNumber, String passportName, String passportDate) {
        this.passportNumber = passportNumber;
        this.passportName = passportName;
        this.passportDate = passportDate;
    }

    public static PassportDTO toDTO(PassportEntity passportEntity) {
        PassportDTO passportDTO = new PassportDTO();
        passportDTO.setPassportId(passportEntity.getPassportId());
        passportDTO.setPassportNumber(passportEntity.getPassportNumber());
        passportDTO.setPassportName(passportEntity.getPassportName());
        passportDTO.setPassportDate(passportEntity.getPassportDate());
        return passportDTO;
    }
}
