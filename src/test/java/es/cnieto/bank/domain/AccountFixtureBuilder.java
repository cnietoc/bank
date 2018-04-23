package es.cnieto.bank.domain;

import es.cnieto.bank.domain.port.AccountRepository;

import java.math.BigDecimal;

public final class AccountFixtureBuilder {
    private AccountRepository accountRepository;
    private String accountId;
    private Bank bank;
    private BigDecimal currentMoney;

    private AccountFixtureBuilder() {
    }

    public static AccountFixtureBuilder anAccount() {
        return new AccountFixtureBuilder();
    }

    public AccountFixtureBuilder withAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        return this;
    }

    public AccountFixtureBuilder withAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public AccountFixtureBuilder withBank(Bank bank) {
        this.bank = bank;
        return this;
    }

    public AccountFixtureBuilder withCurrentMoney(BigDecimal currentMoney) {
        this.currentMoney = currentMoney;
        return this;
    }

    public Account build() {
        return new Account(accountRepository, accountId, bank, currentMoney);
    }
}
