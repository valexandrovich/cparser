package ua.com.valexa.oc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.valexa.oc.service.OcExtractorService;

@SpringBootTest
class OcApplicationTests {

    @Autowired
    OcExtractorService ocExtractorService;

    @Test
    void contextLoad(){
        ocExtractorService.tst("Borgwarner Inc", "DE");

    }

}
