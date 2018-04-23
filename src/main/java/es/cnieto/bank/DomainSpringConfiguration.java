package es.cnieto.bank;

import es.cnieto.bank.domain.TransferAgentUseCase;
import es.cnieto.bank.domain.port.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainSpringConfiguration {
    private final AccountRepository accountRepository;

    public DomainSpringConfiguration(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean
    public TransferAgentUseCase transferAgentUseCase() {
        return new TransferAgentUseCase(accountRepository);
    }
}
