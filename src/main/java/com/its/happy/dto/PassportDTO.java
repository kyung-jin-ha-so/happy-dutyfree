package com.its.happy.dto;

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
}
