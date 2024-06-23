package ua.com.valexa.eintaxidsaver.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.valexa.eintaxidsaver.entity.EintaxidProfile;
import ua.com.valexa.eintaxidsaver.repository.EintaxidProfileRepository;

@Service
public class EintaxidProfileService {

    @Autowired
    EintaxidProfileRepository eintaxidProfileRepository;

    @Transactional
    public EintaxidProfile findByIrsEin(String irsEin){
        return eintaxidProfileRepository.findByIrsEin(irsEin);
    }

    public Page<EintaxidProfile> getAllProfiles(Pageable pageable) {
        return eintaxidProfileRepository.findAll(pageable);
    }
}
