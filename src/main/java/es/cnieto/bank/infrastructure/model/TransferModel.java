package es.cnieto.bank.infrastructure.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferModel {
    private final AccountModel from;
    private final AccountModel to;
    private final BigDecimal amount;
    private final BigDecimal commission;
}
