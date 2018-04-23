package es.cnieto.bank.domain;

import es.cnieto.bank.domain.port.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static es.cnieto.bank.domain.AccountFixtureBuilder.anAccount;
import static es.cnieto.bank.domain.BankFixture.bankOne;
import static es.cnieto.bank.domain.BankFixture.bankTwo;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferAgentUseCaseTest {
    private static final String ACCOUNT_FROM_ID = "FROM ID";
    private static final String ACCOUNT_TO_ID = "TO ID";
    private static final BigDecimal TRANSFER_AMOUNT = valueOf(100);
    private static final BigDecimal INITIAL_FROM_MONEY = BigDecimal.valueOf(1000);
    private static final BigDecimal INITIAL_TO_MONEY = BigDecimal.valueOf(100);
    private static final BigDecimal INTER_BANK_COMMISSION = BigDecimal.valueOf(5);

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransferAgentUseCase transferAgentUseCase;

    @Test
    public void transferIntraBankSuccessful() {
        Account accountFrom = anAccount().withAccountRepository(accountRepository)
                .withBank(bankOne())
                .withAccountId(ACCOUNT_FROM_ID)
                .withCurrentMoney(INITIAL_FROM_MONEY)
                .build();
        Account accountTo = anAccount().withAccountRepository(accountRepository)
                .withBank(bankOne())
                .withAccountId(ACCOUNT_TO_ID)
                .withCurrentMoney(INITIAL_TO_MONEY)
                .build();
        when(accountRepository.find(ACCOUNT_FROM_ID)).thenReturn(accountFrom);
        when(accountRepository.find(ACCOUNT_TO_ID)).thenReturn(accountTo);
        when(accountRepository.applyFrom(ACCOUNT_FROM_ID, TRANSFER_AMOUNT, ZERO)).thenReturn(true);
        when(accountRepository.applyTo(ACCOUNT_TO_ID, TRANSFER_AMOUNT, ZERO)).thenReturn(true);

        boolean result = transferAgentUseCase.transfer(ACCOUNT_FROM_ID, ACCOUNT_TO_ID, TRANSFER_AMOUNT);

        assertTrue(result);
        verify(accountRepository).applyFrom(ACCOUNT_FROM_ID, TRANSFER_AMOUNT, ZERO);
        verify(accountRepository).applyTo(ACCOUNT_TO_ID, TRANSFER_AMOUNT, ZERO);
        assertThat(accountFrom.getCurrentMoney(), equalTo(BigDecimal.valueOf(900)));
        assertThat(accountTo.getCurrentMoney(), equalTo(BigDecimal.valueOf(200)));
    }

    @Test
    public void transferInterBankSuccessful() {
        Account accountFrom = anAccount().withAccountRepository(accountRepository)
                .withBank(bankOne())
                .withAccountId(ACCOUNT_FROM_ID)
                .withCurrentMoney(INITIAL_FROM_MONEY)
                .build();
        Account accountTo = anAccount().withAccountRepository(accountRepository)
                .withBank(bankTwo())
                .withAccountId(ACCOUNT_TO_ID)
                .withCurrentMoney(INITIAL_TO_MONEY)
                .build();
        when(accountRepository.find(ACCOUNT_FROM_ID)).thenReturn(accountFrom);
        when(accountRepository.find(ACCOUNT_TO_ID)).thenReturn(accountTo);
        when(accountRepository.applyFrom(ACCOUNT_FROM_ID, TRANSFER_AMOUNT, INTER_BANK_COMMISSION)).thenReturn(true);
        when(accountRepository.applyTo(ACCOUNT_TO_ID, TRANSFER_AMOUNT, INTER_BANK_COMMISSION)).thenReturn(true);

        boolean result = transferAgentUseCase.transfer(ACCOUNT_FROM_ID, ACCOUNT_TO_ID, TRANSFER_AMOUNT);

        assertTrue(result);
        verify(accountRepository).applyFrom(ACCOUNT_FROM_ID, TRANSFER_AMOUNT, INTER_BANK_COMMISSION);
        verify(accountRepository).applyTo(ACCOUNT_TO_ID, TRANSFER_AMOUNT, INTER_BANK_COMMISSION);
        assertThat(accountFrom.getCurrentMoney(), equalTo(BigDecimal.valueOf(895)));
        assertThat(accountTo.getCurrentMoney(), equalTo(BigDecimal.valueOf(200)));
    }

    @Test
    public void transferInterBankWithErrorOnFrom() {
        Account accountFrom = anAccount().withAccountRepository(accountRepository)
                .withBank(bankOne())
                .withAccountId(ACCOUNT_FROM_ID)
                .withCurrentMoney(INITIAL_FROM_MONEY)
                .build();
        Account accountTo = anAccount().withAccountRepository(accountRepository)
                .withBank(bankTwo())
                .withAccountId(ACCOUNT_TO_ID)
                .withCurrentMoney(INITIAL_TO_MONEY)
                .build();
        when(accountRepository.find(ACCOUNT_FROM_ID)).thenReturn(accountFrom);
        when(accountRepository.find(ACCOUNT_TO_ID)).thenReturn(accountTo);
        when(accountRepository.applyFrom(ACCOUNT_FROM_ID, TRANSFER_AMOUNT, INTER_BANK_COMMISSION)).thenReturn(false);
        when(accountRepository.applyTo(ACCOUNT_TO_ID, TRANSFER_AMOUNT, INTER_BANK_COMMISSION)).thenReturn(true);

        boolean result = transferAgentUseCase.transfer(ACCOUNT_FROM_ID, ACCOUNT_TO_ID, TRANSFER_AMOUNT);

        assertFalse(result);
        verify(accountRepository).applyFrom(ACCOUNT_FROM_ID, TRANSFER_AMOUNT, INTER_BANK_COMMISSION);
        assertThat(accountFrom.getCurrentMoney(), equalTo(INITIAL_FROM_MONEY));
        assertThat(accountTo.getCurrentMoney(), equalTo(INITIAL_TO_MONEY));
    }

    @Test
    public void transferInterBankWithErrorOnTo() {
        Account accountFrom = anAccount().withAccountRepository(accountRepository)
                .withBank(bankOne())
                .withAccountId(ACCOUNT_FROM_ID)
                .withCurrentMoney(INITIAL_FROM_MONEY)
                .build();
        Account accountTo = anAccount().withAccountRepository(accountRepository)
                .withBank(bankTwo())
                .withAccountId(ACCOUNT_TO_ID)
                .withCurrentMoney(INITIAL_TO_MONEY)
                .build();
        when(accountRepository.find(ACCOUNT_FROM_ID)).thenReturn(accountFrom);
        when(accountRepository.find(ACCOUNT_TO_ID)).thenReturn(accountTo);
        when(accountRepository.applyFrom(ACCOUNT_FROM_ID, TRANSFER_AMOUNT, INTER_BANK_COMMISSION)).thenReturn(true);
        when(accountRepository.applyTo(ACCOUNT_TO_ID, TRANSFER_AMOUNT, INTER_BANK_COMMISSION)).thenReturn(false);

        boolean result = transferAgentUseCase.transfer(ACCOUNT_FROM_ID, ACCOUNT_TO_ID, TRANSFER_AMOUNT);

        assertFalse(result);
        verify(accountRepository).applyFrom(ACCOUNT_FROM_ID, TRANSFER_AMOUNT, INTER_BANK_COMMISSION);
        verify(accountRepository).applyTo(ACCOUNT_TO_ID, TRANSFER_AMOUNT, INTER_BANK_COMMISSION);
        verify(accountRepository).revertFrom(ACCOUNT_FROM_ID, TRANSFER_AMOUNT, INTER_BANK_COMMISSION);
        assertThat(accountFrom.getCurrentMoney(), equalTo(INITIAL_FROM_MONEY));
        assertThat(accountTo.getCurrentMoney(), equalTo(INITIAL_TO_MONEY));
    }
}