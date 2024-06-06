package ua.com.valexa.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EintaxidExtractRequest {
    private String companyLink;

    public EintaxidExtractRequest(String companyLink) {
        this.companyLink = companyLink;
    }
}
