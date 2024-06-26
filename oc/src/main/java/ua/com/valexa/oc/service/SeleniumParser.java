package ua.com.valexa.oc.service;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@Service
@Slf4j
public class SeleniumParser {


    private static final List<ProxyConfig> proxies = Arrays.asList(
            new ProxyConfig("154.16.83.90", 50100, "http", false),
            new ProxyConfig("176.106.61.34", 50100, "http", false),
            new ProxyConfig("193.39.209.31", 50100, "http", false),
            new ProxyConfig("141.98.72.164", 50100, "http", false),
            new ProxyConfig("185.155.233.240", 50100, "http", false),
            new ProxyConfig("93.189.231.223", 50100, "http", false),
            new ProxyConfig("88.209.244.110", 50100, "http", false),
            new ProxyConfig("31.6.33.103", 50100, "http", false),
            new ProxyConfig("46.34.44.71", 50100, "http", false),
            new ProxyConfig("185.121.12.69", 50100, "http", false),
            new ProxyConfig("5.183.98.235", 50100, "http", false),
            new ProxyConfig("91.186.215.110", 50100, "http", false),
            new ProxyConfig("151.248.71.249", 50100, "http", false),
            new ProxyConfig("103.31.210.146", 50100, "http", false),
            new ProxyConfig("154.70.153.129", 50100, "http", false),
            new ProxyConfig("181.215.185.71", 50100, "http", false),
            new ProxyConfig("89.116.56.224", 50100, "http", false),
            new ProxyConfig("95.164.145.66", 50100, "http", false),
            new ProxyConfig("94.131.56.40", 50100, "http", false),
            new ProxyConfig("194.5.148.208", 50100, "http", false),
            new ProxyConfig("168.196.236.127", 50100, "http", false),
            new ProxyConfig("138.36.93.252", 50100, "http", false),
            new ProxyConfig("185.68.245.198", 50100, "http", false),
            new ProxyConfig("64.113.1.21", 50100, "http", false),
            new ProxyConfig("74.115.0.53", 50100, "http", false),
            new ProxyConfig("163.5.119.162", 50100, "http", false),
            new ProxyConfig("45.157.37.134", 50100, "http", false),
            new ProxyConfig("185.167.234.184", 50100, "http", false),
            new ProxyConfig("193.41.39.34", 50100, "http", false),
            new ProxyConfig("212.23.210.62", 50100, "http", false),
            new ProxyConfig("102.36.163.37", 50100, "http", false),
            new ProxyConfig("114.66.225.133", 50100, "http", false),
            new ProxyConfig("93.190.247.83", 50100, "http", false),
            new ProxyConfig("82.211.7.106", 50100, "http", false),
            new ProxyConfig("193.124.16.250", 50100, "http", false),
            new ProxyConfig("194.87.114.29", 50100, "http", false),
            new ProxyConfig("185.191.144.113", 50100, "http", false),
            new ProxyConfig("88.209.207.27", 50100, "http", false),
            new ProxyConfig("212.115.124.5", 50100, "http", false),
            new ProxyConfig("45.135.38.109", 50100, "http", false),
            new ProxyConfig("91.201.142.241", 50100, "http", false),
            new ProxyConfig("206.53.54.234", 50100, "http", false),
            new ProxyConfig("103.172.85.149", 50100, "http", false),
            new ProxyConfig("45.152.189.60", 50100, "http", false),
            new ProxyConfig("88.216.34.52", 50100, "http", false),
            new ProxyConfig("103.101.51.140", 50100, "http", false),
            new ProxyConfig("93.152.208.174", 50100, "http", false),
            new ProxyConfig("185.253.45.161", 50100, "http", false),
            new ProxyConfig("131.196.255.102", 50100, "http", false)
            );


    private static final List<String> userAgents = Arrays.asList(
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36"
            // Add more user agents here
    );


    private static final Random random = new Random();

    public static ProxyConfig getRandomProxy() {
        return proxies.get(random.nextInt(proxies.size()));
    }

    public static String getRandomUserAgent() {
        return userAgents.get(random.nextInt(userAgents.size()));
    }


    private WebClient client = new WebClient();

    {
        log.info("Initing Web Client ... ");
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setCssEnabled(false);


//        String proxyHost = "91.186.215.110"; // Replace with your proxy host
//        int proxyPort = 50100; // Replace with your proxy port
        String proxyUser = "secretive777"; // Replace with your proxy username
        String proxyPass = "3V9YNEquLm"; // Replace with your proxy password




        DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) client.getCredentialsProvider();
        credentialsProvider.addCredentials(proxyUser, proxyPass);




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


    public boolean isLoginNeeded(HtmlPage page){
        try {
            HtmlHeading2 paidHead =  (HtmlHeading2) page.getByXPath("/html/body/div[2]/div[2]/div/h2").get(0);
            if(paidHead.asNormalizedText().startsWith("Please sign in")){
                System.out.println("LOGIN NEEDED");
                return true;
            }
        } catch (Exception ex){
            log.error(ex.getMessage());
        }
        return false;
    }

    public void login() {
        String loginUrl = "https://opencorporates.com/users/sign_in";
        String username = "stmccucup@protonmail.com";
        String password = "23.Dfkthf23";

        try {
            HtmlPage loginPage = client.getPage(loginUrl);

            HtmlEmailInput emailInput = (HtmlEmailInput) loginPage.getByXPath("/html/body/div[2]/div[3]/div[1]/form/div[2]/div/input").get(0);
            HtmlPasswordInput passwordInput = (HtmlPasswordInput) loginPage.getByXPath("/html/body/div[2]/div[3]/div[1]/form/div[3]/div/input").get(0);
            HtmlButton submitButton = (HtmlButton) loginPage.getByXPath("/html/body/div[2]/div[3]/div[1]/form/div[6]/div/button").get(0);

            emailInput.setValueAttribute(username);
            passwordInput.setValueAttribute(password);

            HtmlPage resultPage = submitButton.click();

            client.waitForBackgroundJavaScript(3000);

//            if (isLoginNeeded(resultPage)) {
//                log.error("Login failed");
//            } else {
//                log.info("Login successful");
//            }

        } catch (IOException e) {
            log.error("Login error", e);
        }
    }


    List<HtmlListItem> getSearchResults(HtmlPage page){
        List<HtmlListItem> listItems = new ArrayList<HtmlListItem>();
        HtmlUnorderedList companiesList = (HtmlUnorderedList) page.getByXPath("/html/body/div[2]/div[2]/div[1]/div[2]/ul").get(0);
        listItems = companiesList.getByXPath(".//li");
        return listItems;
    }



//    private static final List<ProxyConfig> proxies = Arrays.asList(
//            new ProxyConfig("93.189.231.223", 50100),
//            new ProxyConfig("31.6.33.103", 50100)
//            // Add more proxies here
//    );


    public void tst(){


        String url = "https://opencorporates.com/companies/us_de?branch=false&commit=Go&mode=phrase_prefix&nonprofit=&order=&q=Borgwarner+Inc&type=companies&user=true&utf8=%E2%9C%93";
//        String proxyHost = "31.6.33.103 ";
//        int proxyPort = 50100;
//        String proxyUser = "secretive777";
//        String proxyPass = "3V9YNEquLm";


        ProxyConfig proxyConfig = getRandomProxy();
        client.getOptions().setProxyConfig(proxyConfig);


        String userAgent = getRandomUserAgent();
        client.addRequestHeader("User-Agent", userAgent);

//        ProxyConfig proxyConfig = new ProxyConfig(proxyHost, proxyPort, "http", false);
//        proxyConfig.setProxyHost(proxyHost);
//        proxyConfig.setProxyPort(proxyPort);
//        client.getOptions().setProxyConfig(proxyConfig);

        try {
            HtmlPage page =  client.getPage(url);

            if (isLoginNeeded(page)){
                login();
            }

            page = client.getPage(url);

            List<HtmlListItem> results = getSearchResults(page);
            System.out.println("RES: " +results.size());

//            System.out.println(page.asXml());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//        try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
//            // Set proxy if needed
//            // webClient.getOptions().setProxyConfig(new ProxyConfig(proxyHost, proxyPort));
//            // webClient.getCredentialsProvider().setCredentials(new AuthScope(proxyHost, proxyPort), new UsernamePasswordCredentials(proxyUser, proxyPass));
//
//            webClient.getOptions().setJavaScriptEnabled(true);
//            webClient.getOptions().setCssEnabled(false);
//            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//            webClient.getOptions().setThrowExceptionOnScriptError(false);
//            webClient.getOptions().setTimeout(30000);
//
//            HtmlPage page = webClient.getPage(url);
//
//            // Wait for JavaScript to execute
//            webClient.waitForBackgroundJavaScriptStartingBefore(10000);
//            webClient.waitForBackgroundJavaScript(10000); // Wait for JavaScript to execute
//
//            System.out.println(page.asXml());
//
//        } catch (Exception e) {
//            log.error("ERROR", e);
//        }


    }

}
