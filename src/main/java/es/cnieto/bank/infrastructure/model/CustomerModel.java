package es.cnieto.bank.infrastructure.model;

import lombok.Data;

import java.util.Set;

@Data
public class CustomerModel {
    private final Set<AccountModel> accounts;
}
