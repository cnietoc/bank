package es.cnieto.bank.infrastructure.rest;

import es.cnieto.bank.BanksApplication;
import lombok.ToString;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BanksApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BanksControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void doTransferSuccessfully() {
        ResponseEntity<Object> responseEntity = testRestTemplate.getForEntity("/transfer?from=1&to=2&amount=30", Object.class);

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }

}