package ua.com.valexa.eintaxidsaver.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.valexa.common.dto.EintaxidProfileDto;
import ua.com.valexa.eintaxidsaver.entity.EintaxidProfile;
import ua.com.valexa.eintaxidsaver.repository.EintaxidProfileRepository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class EintaxidSaverService {

    @Autowired
    EintaxidProfileRepository eintaxidProfileRepository;


    ExecutorService executorService = Executors.newFixedThreadPool(20);

    @SneakyThrows
    public CompletableFuture<Void> save(EintaxidProfileDto dto) {
        return CompletableFuture.runAsync(() -> saveDto(dto), executorService);
    }

    @SneakyThrows
    private void saveDto(EintaxidProfileDto dto){
        EintaxidProfile entity = toEntity(dto);
        eintaxidProfileRepository.save(entity);
    }


    private static EintaxidProfile toEntity(EintaxidProfileDto dto) {
        if (dto == null) {
            return null;
        }

        EintaxidProfile entity = new EintaxidProfile();
        entity.setOrgName(dto.getOrgName());
        entity.setIrsEin(dto.getIrsEin());
        entity.setDoingBusinessAs(dto.getDoingBusinessAs());
        entity.setTypeOfBusiness(dto.getTypeOfBusiness());
        entity.setDescription(dto.getDescription());
        entity.setBusinessProfile(dto.getBusinessProfile());
        entity.setBusinessAddress(dto.getBusinessAddress());
        entity.setBusinessAddressLine2(dto.getBusinessAddressLine2());
        entity.setBusinessCity(dto.getBusinessCity());
        entity.setBusinessState(dto.getBusinessState());
        entity.setBusinessZip(dto.getBusinessZip());
        entity.setMailingAddress(dto.getMailingAddress());
        entity.setMailingAddress2(dto.getMailingAddress2());
        entity.setMailingCity(dto.getMailingCity());
        entity.setMailingState(dto.getMailingState());
        entity.setMailingZIP(dto.getMailingZIP());
        entity.setCik(dto.getCik());
        entity.setEndOfFiscalYear(dto.getEndOfFiscalYear());
        entity.setIncState(dto.getIncState());
        entity.setIncSubDiv(dto.getIncSubDiv());
        entity.setIncCountry(dto.getIncCountry());
        entity.setFillingYear(dto.getFillingYear());
        entity.setLink(dto.getLink());
        return entity;
    }


}
