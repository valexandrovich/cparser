package ua.com.valexa.eintaxidlinkharvester.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ua.com.valexa.eintaxidlinkharvester.service.EintaxidHarvesterService;

@RestController
@Slf4j
public class EintexidHarvesterController {


    @Autowired
    EintaxidHarvesterService eintaxidHarvesterService;

    @GetMapping("/harvest")
    public Mono<Void> startHarvesting(){
        log.info("Start harvesting");
       return eintaxidHarvesterService.startHarvesting();
    }

}
