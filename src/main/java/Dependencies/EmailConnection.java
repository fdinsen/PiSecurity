/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dependencies;

import Models.EmailParams;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.AccountApi;
import sibApi.AttributesApi;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.GetAccount;
import sibModel.GetAttributes;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

/**
 *
 * @author gamma
 */
public class EmailConnection implements IEmailConnection {
    private static GetAccount account;
    @Override
    public void sendEmail(String email, String username, String url) {
        if(account == null) {
            setupConnection();
        }
        
        TransactionalEmailsApi api = new TransactionalEmailsApi();
        SendSmtpEmail send = new SendSmtpEmail();
        
        SendSmtpEmailSender sender = new SendSmtpEmailSender().email("gammarikb@gmail.com").name("Pi Forums");
        SendSmtpEmailTo reciever = new SendSmtpEmailTo().email(email).name(username);
        List<SendSmtpEmailTo> recievers = new ArrayList();
        recievers.add(reciever);
        
        send.sender(sender);
        send.setTo(recievers);
        send.setTemplateId(1L);
        send.setParams(new EmailParams(url));
        
        CreateSmtpEmail result;
        try {
            result = api.sendTransacEmail(send);
            System.out.println(result);
        } catch (ApiException ex) {
            Logger.getLogger(this.getClass().getName()).error("Activation email was not sent successfully to " + email + ", " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    private static void setupConnection() {
        ApiClient defaultClient = Configuration.getDefaultApiClient();

        // Configure API key authorization: api-key
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey("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");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //apiKey.setApiKeyPrefix("Token");

        AccountApi apiInstance = new AccountApi();
        try {
            GetAccount result = apiInstance.getAccount();
            account = result;
            System.out.println(result);
        } catch (ApiException e) {
            Logger.getLogger("EmailConnection").error("Email connection could not be set up");
            System.err.println("Exception when calling AccountApi#getAccount");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        setupConnection();
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        TransactionalEmailsApi api = new TransactionalEmailsApi();
        SendSmtpEmail send = new SendSmtpEmail();
        SendSmtpEmailSender sender = new SendSmtpEmailSender().email("gammarikb@gmail.com").name("test sender");
        SendSmtpEmailTo reciever = new SendSmtpEmailTo().email("gammarik@gmail.com").name("Frederik");
        List<SendSmtpEmailTo> recievers = new ArrayList();
        recievers.add(reciever);
        send.sender(sender);
        send.setTo(recievers);
        //send.setHtmlContent("<p>hi</p>");
        send.setTemplateId(1L);
        send.setParams(new EmailParams("http://localhost:8080/testssss"));
        CreateSmtpEmail result;
        try {
            result = api.sendTransacEmail(send);
            System.out.println(result);
        } catch (ApiException ex) {
            ex.printStackTrace();
        }

    }
}
