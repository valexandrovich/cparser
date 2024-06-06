package ua.com.valexa.eintaxidsaver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.valexa.common.dto.EintaxidExtractRequest;
import ua.com.valexa.common.dto.EintaxidProfileDto;
import ua.com.valexa.eintaxidsaver.repository.EintaxidProfileRepository;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class QueueListener {

    @Autowired
    EintaxidSaverService eintaxidSaverService;


    @RabbitListener(queues = "cs.eintaxid.saver")
    public void receiveDownloaderMessage(EintaxidProfileDto dto) {
        log.info("EintaxidSaver get request: " + dto);

        CompletableFuture<Void> cfuture = eintaxidSaverService.save(dto);
    }





}
