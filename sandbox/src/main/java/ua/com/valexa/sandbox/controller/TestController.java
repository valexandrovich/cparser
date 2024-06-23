package ua.com.valexa.sandbox.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/tst")
    public String tst(){
        return "Test is ok";
    }

}
