package es.cnieto.bank.domain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class IntraBankTransfer extends Transfer {
    private static final double MAX_TRANSFERENCE_ALLOWED = 1000;
    private final Account accountFrom;
    private final Account accountTo;
    private final BigDecimal amount;


    @Override
    protected boolean isAllowed() {
        return amount.doubleValue() <= MAX_TRANSFERENCE_ALLOWED;
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
        return BigDecimal.ZERO;
    }
}
