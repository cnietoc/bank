package es.cnieto.bank.domain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class InterBankTransfer extends Transfer {
    private static final BigDecimal COMMISSION = BigDecimal.valueOf(5);
    private final Account accountFrom;
    private final Account accountTo;
    private final BigDecimal amount;

    @Override
    protected boolean isAllowed() {
        return true;
    }

    @Override
    protected Account getAccountFrom() {
        return accountFrom;
    }

    @Override
    protected Account getAccountTo() {
        return accountTo;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public BigDecimal getCommission() {
        return COMMISSION;
    }
}
