package ua.com.valexa.eintaxidextractor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.valexa.common.dto.EtiExtractRequest;
import ua.com.valexa.common.dto.EtiProfileDto;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class QueueListener {

    @Autowired
    EintaxidExtractorService eintaxidExtractorService;

    @Autowired
    RabbitTemplate rabbitTemplate;


    @RabbitListener(queues = "cs.eintaxid.extractor", errorHandler = "queueListenerErrorHandler")
    public void receiveDownloaderMessage(EtiExtractRequest request) {
        log.info("EintaxidExtractor get request: " + request.getCompanyLink());
        CompletableFuture<EtiProfileDto> cfuture = eintaxidExtractorService.extractFuture(request);
        cfuture.thenAccept(this::sendResponse);
    }


    private void sendResponse(EtiProfileDto etiProfileDto) {
        log.info("Sending to save EintaxidProfile: " + etiProfileDto);
        rabbitTemplate.convertAndSend("cs.eintaxid.saver", etiProfileDto);


    }


}
