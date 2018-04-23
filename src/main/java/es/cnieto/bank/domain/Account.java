package es.cnieto.bank.domain;

import es.cnieto.bank.domain.port.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Account {
    private final AccountRepository accountRepository;
    private final String accountId;
    private final Bank bank;
    private BigDecimal currentMoney = BigDecimal.ZERO;

    public boolean apply(Transfer transfer) {
        boolean persisted = persist(transfer);
        if (persisted)
            updateMoney(transfer);
        return persisted;
    }

    private void updateMoney(Transfer transfer) {
        if (transfer.isFrom(this)) {
            currentMoney = currentMoney.subtract(transfer.getAmount()).subtract(transfer.getCommission());
        } else if (transfer.isTo(this)) {
            currentMoney = currentMoney.add(transfer.getAmount());
        }
    }

    private boolean persist(Transfer transfer) {
        if (transfer.isFrom(this)) {
            return accountRepository.applyFrom(accountId, transfer.getAmount(), transfer.getCommission());
        } else if (transfer.isTo(this)) {
            return accountRepository.applyTo(accountId, transfer.getAmount(), transfer.getCommission());
        } else {
            return false;
        }
    }

    public void revert(Transfer transfer) {
        if (transfer.isFrom(this)) {
            accountRepository.revertFrom(accountId, transfer.getAmount(), transfer.getCommission());
            currentMoney = currentMoney.add(transfer.getAmount()).add(transfer.getCommission());
        } else if (transfer.isTo(this)) {
            accountRepository.revertTo(accountId, transfer.getAmount(), transfer.getCommission());
            currentMoney = currentMoney.subtract(transfer.getAmount());
        }
    }

    public boolean isSameBankAs(Account otherAccount) {
        return bank.equals(otherAccount.bank);
    }

    public boolean haveAtLeast(BigDecimal amount) {
        return currentMoney.compareTo(amount) >= 0;
    }
}
