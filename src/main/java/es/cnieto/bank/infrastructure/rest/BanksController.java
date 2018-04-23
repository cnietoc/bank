package es.cnieto.bank.infrastructure.rest;

import es.cnieto.bank.domain.TransferAgentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class BanksController {
    private final TransferAgentUseCase transferAgentUseCase;

    @RequestMapping("/transfer")
    public ResponseEntity transfer(@RequestParam("from") String accountFrom,
                                   @RequestParam("to") String accountTo,
                                   @RequestParam("amount") BigDecimal amount) {

        if (transferAgentUseCase.transfer(accountFrom, accountTo, amount)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }
}
