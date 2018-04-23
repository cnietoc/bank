package es.cnieto.bank.infrastructure.repository;

import es.cnieto.bank.domain.Account;
import es.cnieto.bank.domain.Bank;
import es.cnieto.bank.domain.port.AccountRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DummyAccountRepository implements AccountRepository {
    private static final String ACCOUNT_ID_ONE = "1";
    private static final String ACCOUNT_ID_TWO = "2";
    private static final String ACCOUNT_ID_THREE = "3";
    private static final String ACCOUNT_ID_FOUR = "4";
    private final Map<String, Account> accountOnMemory = new HashMap<>();
    private final Bank dummyBank = new Bank("DummyBank");
    private final Bank fakeBank = new Bank("FakeBank");

    public DummyAccountRepository() {
        accountOnMemory.put(ACCOUNT_ID_ONE, new Account(this, ACCOUNT_ID_ONE, dummyBank, BigDecimal.valueOf(1000)));
        accountOnMemory.put(ACCOUNT_ID_TWO, new Account(this, ACCOUNT_ID_TWO, dummyBank, BigDecimal.valueOf(200)));
        accountOnMemory.put(ACCOUNT_ID_THREE, new Account(this, ACCOUNT_ID_THREE, fakeBank, BigDecimal.valueOf(1000)));
        accountOnMemory.put(ACCOUNT_ID_FOUR, new Account(this, ACCOUNT_ID_FOUR, fakeBank, BigDecimal.valueOf(200)));
    }

    @Override
    public Account find(String accountId) {
        return accountOnMemory.get(accountId);
    }

    @Override
    public boolean applyFrom(String accountId, BigDecimal amount, BigDecimal commission) {
        return true;
    }

    @Override
    public boolean applyTo(String accountId, BigDecimal amount, BigDecimal commission) {
        return true;
    }

    @Override
    public void revertFrom(String accountId, BigDecimal amount, BigDecimal commission) {
        // empty because of dummy implementation
    }

    @Override
    public void revertTo(String accountId, BigDecimal amount, BigDecimal commission) {
        // empty because of dummy implementation
    }
}
