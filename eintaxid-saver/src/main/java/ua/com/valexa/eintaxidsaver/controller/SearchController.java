package ua.com.valexa.eintaxidsaver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.valexa.eintaxidsaver.entity.EintaxidProfile;
import ua.com.valexa.eintaxidsaver.repository.EintaxidProfileRepository;
import ua.com.valexa.eintaxidsaver.service.EintaxidProfileService;

@RestController
public class SearchController {

    @Autowired
    EintaxidProfileService eintaxidProfileService;

    @GetMapping("/company")
    public EintaxidProfile getCompanyByIrsEin(
            @RequestParam String irsEin
    ) {
        EintaxidProfile result = eintaxidProfileService.findByIrsEin(irsEin);
        return result;
    }

    @GetMapping("/all")
    public Page<EintaxidProfile> getAllProfiles(Pageable pageable) {
        return eintaxidProfileService.getAllProfiles(pageable);
    }

}
