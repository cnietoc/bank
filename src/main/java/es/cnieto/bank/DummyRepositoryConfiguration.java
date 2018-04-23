package es.cnieto.bank;

import es.cnieto.bank.domain.port.AccountRepository;
import es.cnieto.bank.infrastructure.repository.DummyAccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DummyRepositoryConfiguration {
    @Bean
    public AccountRepository accountRepository() {
        return new DummyAccountRepository();
    }
}
