package es.cnieto.bank.domain.port;

import es.cnieto.bank.domain.Account;

import java.math.BigDecimal;

public interface AccountRepository {
    Account find(String accountId);
    boolean applyFrom(String accountId, BigDecimal amount, BigDecimal commission);
    boolean applyTo(String accountId, BigDecimal amount, BigDecimal commission);

    void revertFrom(String accountId, BigDecimal amount, BigDecimal commission);

    void revertTo(String accountId, BigDecimal amount, BigDecimal commission);
}
