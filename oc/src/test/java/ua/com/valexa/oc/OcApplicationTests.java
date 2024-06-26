package ua.com.valexa.oc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.valexa.oc.service.SeleniumParser;
import ua.com.valexa.oc.service.OcExtractorService;

@SpringBootTest
class OcApplicationTests {

    @Autowired
    OcExtractorService ocExtractorService;

    @Autowired
    SeleniumParser seleniumParser;

    @Test
    void contextLoad(){


//        seleniumParser.tst();

//        for (int i = 1; i <= 100; i++) {
//            List<String> res = ocExtractorService.getSearchLinks("Borgwarner Inc", "Delaware");
//            System.out.println("I=" + i + "; SIZE=" + res.size()  );
//        }
        int max = 100;
        for (int i = 0; i < max; i++) {
            System.out.println("I: " + i);
            seleniumParser.tst();
        }



//        System.out.println(res);

    }

}
