package es.cnieto.bank.infrastructure.model;

import lombok.Data;

import java.util.List;

@Data
public class AccountModel {
    private final List<TransferModel> transfers;
}
