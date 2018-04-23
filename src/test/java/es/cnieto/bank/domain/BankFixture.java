package es.cnieto.bank.domain;

public class BankFixture {
    public static Bank bankOne() {
        return new Bank("Bank 1");
    }

    public static Bank bankTwo() {
        return new Bank("Bank 2");
    }
}
