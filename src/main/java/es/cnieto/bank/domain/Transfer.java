package es.cnieto.bank.domain;

import java.math.BigDecimal;

public abstract class Transfer {
    boolean apply() {
        if(!isPossible()) {
            return false;
        }
        boolean fromResult = getAccountFrom().apply(this);
        boolean toResult = false;
        if (fromResult) {
            toResult = getAccountTo().apply(this);
            if (!toResult) {
                getAccountFrom().revert(this);
            }
        }
        return fromResult && toResult;
    }

    private boolean isPossible() {
        return isAllowed() && fromAccountHasEnoughMoney();
    }

    protected abstract boolean isAllowed();

    private boolean fromAccountHasEnoughMoney() {
        return getAccountFrom().haveAtLeast(getAmount().add(getCommission()));
    }

    public boolean isFrom(Account account) {
        return getAccountFrom().equals(account);
    }

    public boolean isTo(Account account) {
        return getAccountTo().equals(account);
    }

    protected abstract Account getAccountFrom();

    protected abstract Account getAccountTo();

    public abstract BigDecimal getAmount();

    public abstract BigDecimal getCommission();
}
