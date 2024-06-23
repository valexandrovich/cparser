package ua.com.valexa.oc.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OcExtractorService {


    private HtmlPage loginToWebsite(WebClient webClient) throws Exception {
        String loginUrl = "https://opencorporates.com/users/sign_in";
        HtmlPage loginPage = webClient.getPage(loginUrl);


        HtmlEmailInput emailInput = (HtmlEmailInput) loginPage.getByXPath("/html/body/div[2]/div[3]/div[1]/form/div[2]/div/input").get(0);
        HtmlPasswordInput passwordInput = (HtmlPasswordInput) loginPage.getByXPath("/html/body/div[2]/div[3]/div[1]/form/div[3]/div/input").get(0);
        HtmlButton submitButton = (HtmlButton) loginPage.getByXPath("/html/body/div[2]/div[3]/div[1]/form/div[6]/div/button").get(0);
        // Replace the below with actual form field names and login URL
//        HtmlForm form = loginPage.getFormByName("loginForm");
//        HtmlTextInput usernameField = form.getInputByName("email");
//        HtmlPasswordInput passwordField = form.getInputByName("password");
//        HtmlSubmitInput submitButton = form.getInputByName("submit");

        // Set credentials
//        usernameField.setValueAttribute("your-username");
//        passwordField.setValueAttribute("your-password");

        emailInput.setValueAttribute("stmccucup@protonmail.com");
        passwordInput.setValueAttribute("23.Dfkthf23");


        // Submit the form
        HtmlPage homePage = submitButton.click();
        return homePage;
    }


    private boolean isLoginNeeded(HtmlPage page){
        try {
            HtmlHeading2 paidHead =  (HtmlHeading2) page.getByXPath("/html/body/div[2]/div[2]/div/h2").get(0);
            if(paidHead.asNormalizedText().startsWith("Please sign in")){
                return true;
            }
        } catch (Exception ex){
            log.error(ex.getMessage());
        }
        return false;
    }


    private List<String> getSearchLinks(String companyName, String incState){
        try (WebClient webClient = new WebClient()) {

            String jurisdiction = "us_de";

            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append("https://opencorporates.com/companies/");
            urlBuilder.append(jurisdiction);
            urlBuilder.append("?branch=false&commit=Go&mode=phrase_prefix&nonprofit=&order=&q=");
            urlBuilder.append(companyName.replace(" ", "+"));
            urlBuilder.append("&type=companies&user=true&utf8=%E2%9C%93");

            String url = urlBuilder.toString();


            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setCssEnabled(false);

            HtmlPage page = webClient.getPage(url);

            if (isLoginNeeded(page)){
                System.out.println("NEED LOGIN!!");
                page = loginToWebsite(webClient);
            }
            page = webClient.getPage(url);
            HtmlUnorderedList companiesList = (HtmlUnorderedList) page.getByXPath("/html/body/div[2]/div[2]/div[1]/div[2]/ul").get(0);


            System.out.println(page);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void tst(String companyName, String incState) {



    }
//        try (WebClient webClient = new WebClient()) {
//            webClient.getOptions().setJavaScriptEnabled(true);
//            webClient.getOptions().setThrowExceptionOnScriptError(false);
//            webClient.getOptions().setCssEnabled(false);
//            HtmlPage page = webClient.getPage("https://opencorporates.com/companies?utf8=%E2%9C%93&q=Bio-techne+Corp&commit=Go&jurisdiction_code=&country_code=us&type=companies&user=true&utf8=%E2%9C%93&controller=searches&action=search_companies&mode=best_fields&search_fields%5B%5D=name&search_fields%5B%5D=previous_names&search_fields%5B%5D=company_number&search_fields%5B%5D=other_company_numbers&branch=&nonprofit=&order=");
//            webClient.waitForBackgroundJavaScript(10000);
//            System.out.println(page);
////            links.addAll(page.getByXPath("/html/body/div[2]/div/div[1]/div/div[2]/a")
////                    .stream()
////                    .map(el -> (HtmlAnchor) el)
////                    .map(el -> "https:" + el.getHrefAttribute())
////                    .collect(Collectors.toSet()));
//
//        } catch (Exception e) {
//            log.error("getLevel2urls: " + e.getMessage());
//        }
//    }

}
