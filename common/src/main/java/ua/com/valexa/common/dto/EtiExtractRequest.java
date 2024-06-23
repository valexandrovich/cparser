package ua.com.valexa.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EtiExtractRequest {
    private String companyLink;

    public EtiExtractRequest(String companyLink) {
        this.companyLink = companyLink;
    }
}
