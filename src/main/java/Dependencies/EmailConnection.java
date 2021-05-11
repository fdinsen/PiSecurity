/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dependencies;

import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.AccountApi;
import sibApi.AttributesApi;
import sibModel.GetAccount;
import sibModel.GetAttributes;

/**
 *
 * @author gamma
 */
public class EmailConnection implements IEmailConnection {

    @Override
    public void sendEmail(String email, String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        setupConnection();
    }

    private static void setupConnection() {
        ApiClient defaultClient = Configuration.getDefaultApiClient();

        // Configure API key authorization: api-key
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey("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");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //apiKey.setApiKeyPrefix("Token");

        AccountApi apiInstance = new AccountApi();
        AttributesApi apiInstancea = new AttributesApi();
        try {
            GetAccount result = apiInstance.getAccount();
            System.out.println(result);
            GetAttributes results = apiInstancea.getAttributes();
            System.out.println(results);
        } catch (ApiException e) {
            System.err.println("Exception when calling AccountApi#getAccount");
            e.printStackTrace();
        }
    }
}
