package ua.com.valexa.eintaxidextractor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.valexa.common.dto.EintaxidExtractRequest;
import ua.com.valexa.common.dto.EintaxidProfileDto;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class QueueListener {

    @Autowired
    EintaxidExtractorService eintaxidExtractorService;

    @Autowired
    RabbitTemplate rabbitTemplate;


    @RabbitListener(queues = "cs.eintaxid.extractor", errorHandler = "queueListenerErrorHandler")
    public void receiveDownloaderMessage(EintaxidExtractRequest request) {
        log.info("EintaxidExtractor get request: " + request.getCompanyLink());
        CompletableFuture<EintaxidProfileDto> cfuture = eintaxidExtractorService.extractFuture(request);
        cfuture.thenAccept(this::sendResponse);
    }


    private void sendResponse(EintaxidProfileDto eintaxidProfileDto) {
        log.info("Sending to save EintaxidProfile: " + eintaxidProfileDto);
        rabbitTemplate.convertAndSend("cs.eintaxid.saver", eintaxidProfileDto);


    }


}
