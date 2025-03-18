package com.devsu.hackerearth.backend.account.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankStatementReportDto {

    private String clientDni;
    private String clientName;
    private List<BankStatementDto> transactions;

}
