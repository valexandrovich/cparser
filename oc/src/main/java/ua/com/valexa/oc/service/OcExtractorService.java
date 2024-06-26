package ua.com.valexa.oc.service;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ua.com.valexa.common.dto.OCProfile;
import ua.com.valexa.common.enums.StateMapping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OcExtractorService {


    private final MessageSource messageSource;

    public OcExtractorService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    WebClient client = new WebClient();

    {
        System.out.println("INIT BLOCK");
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setCssEnabled(false);

        // Set proxy host and port
        String proxyHost = "141.98.72.164"; // Replace with your proxy host
        int proxyPort = 50100; // Replace with your proxy port

        // Set proxy configuration
        ProxyConfig proxyConfig = new ProxyConfig();
        proxyConfig.setProxyHost(proxyHost);
        proxyConfig.setProxyPort(proxyPort);
        client.getOptions().setProxyConfig(proxyConfig);

        // Set proxy authentication
        String proxyUsername = "secretive777"; // Replace with your proxy username
        String proxyPassword = "3V9YNEquLm"; // Replace with your proxy password
        BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(proxyHost, proxyPort),
                new UsernamePasswordCredentials(proxyUsername, proxyPassword)
        );
        client.setCredentialsProvider(credsProvider);

        client.setJavaScriptErrorListener(new JavaScriptErrorListener() {
            @Override
            public void scriptException(HtmlPage page, com.gargoylesoftware.htmlunit.ScriptException scriptException) {
                // Do nothing, suppress JavaScript errors
            }

            @Override
            public void timeoutError(HtmlPage page, long allowedTime, long executionTime) {
                // Do nothing, suppress JavaScript errors
            }

            @Override
            public void malformedScriptURL(HtmlPage page, String url, MalformedURLException malformedURLException) {
                // Do nothing, suppress JavaScript errors
            }

            @Override
            public void loadScriptError(HtmlPage page, URL scriptUrl, Exception exception) {
                // Do nothing, suppress JavaScript errors
            }

            @Override
            public void warn(String message, String sourceName, int line, String lineSource, int lineOffset) {
                // Do nothing
            }
        });



        client.setWebConnection(new WebConnectionWrapper(client) {
            @Override
            public WebResponse getResponse(WebRequest request) throws  IOException {
                WebResponse response = super.getResponse(request);
                if (response.getStatusCode() == 403) {
                    String emptyContent = "403 Forbidden";
                    WebResponseData data = new WebResponseData(
                            emptyContent.getBytes(StandardCharsets.UTF_8),
                            403,
                            "403 Forbidden",
                            response.getResponseHeaders()
                    );
                    return new WebResponse(data, request, response.getLoadTime());
                }
                return response;
            }
        });


    }


    public void tst2(String companyName, String incState){
        StateMapping stateShort = StateMapping.getByState(incState.toUpperCase());
        String jurisdiction = "us_" + stateShort.toString().toLowerCase();
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://opencorporates.com/companies/");
        urlBuilder.append(jurisdiction);
        urlBuilder.append("?branch=false&commit=Go&mode=phrase_prefix&nonprofit=&order=&q=");
        urlBuilder.append(companyName.replace(" ", "+"));
        urlBuilder.append("&type=companies&user=true&utf8=%E2%9C%93");
        String url = urlBuilder.toString();
        log.info("Searching URL: " + url);


        try {
            HtmlPage page = client.getPage(url);
            System.out.println("AFTER");
//            System.out.println(page.asXml());
        } catch (Exception e) {
            log.error("ERROR PAGE");
//            log.error(e.getMessage());
//            throw new RuntimeException(e);
        }

    }

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

    public List<HtmlListItem> getSearchResults(String searchUrl){
        List<HtmlListItem> listItems = new ArrayList<HtmlListItem>();
        try (WebClient webClient = new WebClient()) {
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setCssEnabled(false);

            webClient.setJavaScriptErrorListener(new JavaScriptErrorListener() {
                @Override
                public void scriptException(HtmlPage page, com.gargoylesoftware.htmlunit.ScriptException scriptException) {
                    // Do nothing, suppress JavaScript errors
                }

                @Override
                public void timeoutError(HtmlPage page, long allowedTime, long executionTime) {
                    // Do nothing, suppress JavaScript errors
                }

                @Override
                public void malformedScriptURL(HtmlPage page, String url, MalformedURLException malformedURLException) {
                    // Do nothing, suppress JavaScript errors
                }

                @Override
                public void loadScriptError(HtmlPage page, URL scriptUrl, Exception exception) {
                    // Do nothing, suppress JavaScript errors
                }

                @Override
                public void warn(String message, String sourceName, int line, String lineSource, int lineOffset) {
                    // Do nothing
                }
            });

            System.out.println(searchUrl);
            HtmlPage page = webClient.getPage(searchUrl);
            if (isLoginNeeded(page)){
                page = loginToWebsite(webClient);
            }
            page = webClient.getPage(searchUrl);
            HtmlUnorderedList companiesList = (HtmlUnorderedList) page.getByXPath("/html/body/div[2]/div[2]/div[1]/div[2]/ul").get(0);
            listItems = companiesList.getByXPath(".//li");
            return listItems;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return listItems;

    }

    public List<String> filterAndExtractLinks(List<HtmlListItem> items){
        List<String> links = new ArrayList<>();
        for (HtmlListItem item : items) {
                List<?> anchorsWithIcon = item.getByXPath(".//a[i[@class='icon-fullscreen' and @rel='tooltip' and @data-original-title='this is a controlling company']]");
                if (anchorsWithIcon.isEmpty()) {
                    HtmlAnchor anchor = (HtmlAnchor) item.getByXPath(".//a").get(1);
                    String href = anchor.getHrefAttribute();
                    links.add("https://opencorporates.com/" + href);
                }
            }
        return links;
    }


    public List<OCProfile> getDataFromLinks(List<String> links){
        List<OCProfile> result = new ArrayList<>();
        for (String link : links){
            try (WebClient webClient = new WebClient()) {
                webClient.getOptions().setJavaScriptEnabled(true);
                webClient.getOptions().setThrowExceptionOnScriptError(false);
                webClient.getOptions().setCssEnabled(false);

                webClient.setJavaScriptErrorListener(new JavaScriptErrorListener() {
                    @Override
                    public void scriptException(HtmlPage page, ScriptException scriptException) {
                        // Do nothing, suppress JavaScript errors
                    }

                    @Override
                    public void timeoutError(HtmlPage page, long allowedTime, long executionTime) {
                        // Do nothing, suppress JavaScript errors
                    }

                    @Override
                    public void malformedScriptURL(HtmlPage page, String url, MalformedURLException malformedURLException) {
                        // Do nothing, suppress JavaScript errors
                    }

                    @Override
                    public void loadScriptError(HtmlPage page, URL scriptUrl, Exception exception) {
                        // Do nothing, suppress JavaScript errors
                    }

                    @Override
                    public void warn(String message, String sourceName, int line, String lineSource, int lineOffset) {
                        // Do nothing
                    }
                });

                HtmlPage page = webClient.getPage(link);
                if (isLoginNeeded(page)){
                    page = loginToWebsite(webClient);
                }
                page = webClient.getPage(link);
                HtmlDefinitionList list = (HtmlDefinitionList) page.getByXPath("/html/body/div[2]/div[2]/div[1]/div[1]/div/dl");

                Map<String, String> map = new HashMap<>();

                // Get all dt and dd elements within the dl
                List<?> terms = list.getByXPath(".//dt");
                List<?> descriptions = list.getByXPath(".//dd");

                // Iterate over the terms and descriptions
                for (int i = 0; i < terms.size(); i++) {
                    HtmlDefinitionTerm term = (HtmlDefinitionTerm) terms.get(i);
                    HtmlDefinitionDescription description = (HtmlDefinitionDescription) descriptions.get(i);
//                    map.put(term.hasFeature(), description.hasFeature());
                }

                // Print the map
                map.forEach((key, value) -> System.out.println(key + ": " + value));

//                System.out.println(page);

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return result;
    }

    public List<String> getSearchLinks(String companyName, String incState){
        try (WebClient webClient = new WebClient()) {

            StateMapping stateShort = StateMapping.getByState(incState.toUpperCase());

            String jurisdiction = "us_" + stateShort.toString().toLowerCase();


            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append("https://opencorporates.com/companies/");
            urlBuilder.append(jurisdiction);
            urlBuilder.append("?branch=false&commit=Go&mode=phrase_prefix&nonprofit=&order=&q=");
            urlBuilder.append(companyName.replace(" ", "+"));
            urlBuilder.append("&type=companies&user=true&utf8=%E2%9C%93");

            String url = urlBuilder.toString();



            List<HtmlListItem> resultsList =  getSearchResults(url);

            return resultsList.stream().map(i -> i.asXml()).collect(Collectors.toList());

//            List<String> links = filterAndExtractLinks(resultsList);
//            List<OCProfile> profiles = getDataFromLinks(links);

//            System.out.println(profiles);


//            webClient.getOptions().setJavaScriptEnabled(true);
//            webClient.getOptions().setThrowExceptionOnScriptError(false);
//            webClient.getOptions().setCssEnabled(false);
//
//            HtmlPage page = webClient.getPage(url);
//
//            if (isLoginNeeded(page)){
//                System.out.println("NEED LOGIN!!");
//                page = loginToWebsite(webClient);
//            }
//            page = webClient.getPage(url);
//            HtmlUnorderedList companiesList = (HtmlUnorderedList) page.getByXPath("/html/body/div[2]/div[2]/div[1]/div[2]/ul").get(0);
//
//            List<HtmlListItem> listItems = companiesList.getByXPath(".//li");
//            for (HtmlListItem item : listItems) {
//                // Check if the li contains an a element with the specific i element inside
//                List<?> anchorsWithIcon = item.getByXPath(".//a[i[@class='icon-fullscreen' and @rel='tooltip' and @data-original-title='this is a controlling company']]");
//
//                // If the list is empty, it means the specific a element with i element inside is not present
//                if (anchorsWithIcon.isEmpty()) {
//                    System.out.println("This li does not contain the specified a element with i element inside: " + item.asText());
//                } else {
//                    System.out.println("This li contains the specified a element with i element inside: " + item.asText());
//                }
//            }
//
////
////            List<HtmlListItem> listItems = companiesList.getByXPath(".//li");
////            for (HtmlListItem item : listItems) {
////                System.out.println(item.asText());
////            }
//
//            System.out.println(page);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        return null;
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
