package es.cnieto.bank.domain;

import es.cnieto.bank.domain.port.AccountRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class TransferAgentUseCase {
    private final AccountRepository accountRepository;

    public boolean transfer(String fromAccountId, String toAccountId, BigDecimal amount) {
        Account accountFrom = accountRepository.find(fromAccountId);
        Account accountTo = accountRepository.find(toAccountId);

        Transfer transfer = accountFrom.isSameBankAs(accountTo)
                ? new IntraBankTransfer(accountFrom, accountTo, amount)
                : new InterBankTransfer(accountFrom, accountTo, amount);

        return transfer.apply();
    }
}
