package es.cnieto.bank.infrastructure.model;

import lombok.Data;

import java.util.Set;

@Data
public class BankModel {
    private final String name;
    private final Set<CustomerModel> customers;
}
